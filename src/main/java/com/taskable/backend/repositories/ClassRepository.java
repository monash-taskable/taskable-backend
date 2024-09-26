package com.taskable.backend.repositories;

import com.taskable.backend.exception_handling.NotFoundOnNull;
import com.taskable.backend.utils.DbMapper;
import com.taskable.backend.utils.DbUtils;
import com.taskable.protobufs.AnnouncementProto.*;
import com.taskable.protobufs.PersistenceProto.BasicInfo;
import com.taskable.protobufs.PersistenceProto.ClassroomMember;
import com.taskable.protobufs.PersistenceProto.Classroom;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.taskable.jooq.Tables.ANNOUNCEMENT;
import static com.taskable.jooq.tables.User.USER;
import static com.taskable.jooq.tables.Classroom.CLASSROOM;
import static com.taskable.jooq.tables.ClassroomUser.CLASSROOM_USER;

@Repository
public class ClassRepository {

    private final DSLContext dsl;

    private static final Logger logger = LoggerFactory.getLogger(ClassRepository.class);

    @Autowired
    public ClassRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    // FIX TRANSACTIONAL
    public Classroom storeClass(Integer userId, String name, String date, String description) {
        var res = dsl.insertInto(CLASSROOM)
                .set(CLASSROOM.NAME, name)
                .set(CLASSROOM.CREATED_AT, DbUtils.getDateTime(date))
                .set(CLASSROOM.DESCRIPTION, description)
                .set(CLASSROOM.ARCHIVED, (byte) 0)
                .returning()
                .fetchOneInto(CLASSROOM);
        return DbMapper.map(res);
    }

    @NotFoundOnNull(message = "Resource not found")
    public Classroom getClassById(Integer classId) {
        var rec = dsl.select(CLASSROOM.fields())
                .from(CLASSROOM)
                .where(CLASSROOM.ID.eq(classId))
                .fetchOneInto(CLASSROOM);
        return rec != null ? DbMapper.map(rec) : null;
    }

    public void addUserToClass(Integer userId, Integer classId, String role) {
        dsl.insertInto(CLASSROOM_USER)
            .set(CLASSROOM_USER.CLASSROOM_ID, classId)
            .set(CLASSROOM_USER.USER_ID, userId)
            .set(CLASSROOM_USER.ROLE, role)
            .onDuplicateKeyIgnore()
            .execute();
    }

    public void addUsersToClass(List<Integer> userIds, Integer classId, String role) {
        // TODO: test insert empty
        var insertStep = dsl.insertInto(CLASSROOM_USER,
                CLASSROOM_USER.CLASSROOM_ID,
                CLASSROOM_USER.USER_ID,
                CLASSROOM_USER.ROLE);

        for (var userId : userIds) {
            insertStep.values(classId, userId, role);
        }

        insertStep.onDuplicateKeyIgnore().execute();
    }

    public Map<String, Integer> getUserIdsInClassByEmails(List<String> emails, Integer classId) {
        return dsl.select(USER.EMAIL, CLASSROOM_USER.USER_ID)
                .from(CLASSROOM_USER)
                .join(USER)
                .on(CLASSROOM_USER.USER_ID.eq(USER.ID))
                    .and(CLASSROOM_USER.CLASSROOM_ID.eq(classId))
                .where(USER.EMAIL.in(emails))
                .fetchMap(USER.EMAIL, CLASSROOM_USER.USER_ID);
    }

    @NotFoundOnNull(message = "Resource not found when getting role of user")
    public String getUserRoleInClass(Integer userId, Integer classId) {
        return dsl.select(CLASSROOM_USER.ROLE)
                .from(CLASSROOM_USER)
                .where(CLASSROOM_USER.USER_ID.eq(userId))
                .and(CLASSROOM_USER.CLASSROOM_ID.eq(classId))
                .fetchOne(CLASSROOM_USER.ROLE);
    }

    public List<Pair<Classroom, String>> getClassesAndRolesByUserId(Integer userId) {
        var results = dsl.select(CLASSROOM.fields())
                .select(CLASSROOM_USER.ROLE)
                .from(CLASSROOM)
                .join(CLASSROOM_USER)
                .on(CLASSROOM.ID.eq(CLASSROOM_USER.CLASSROOM_ID))
                .where(CLASSROOM_USER.USER_ID.eq(userId))
                .fetch();
        ArrayList<Pair<Classroom, String>> classRolePairList = new ArrayList<>();
        for (Record record : results) {
            var pair = Pair.<Classroom, String>of(DbMapper.map(record.into(CLASSROOM)), record.get(CLASSROOM_USER.ROLE));
            classRolePairList.add(pair);
        }
        return classRolePairList;
    }

    public boolean checkUserInClass(Integer userId, Integer classId) {
        return dsl.fetchExists(
                dsl.selectOne()
                    .from(CLASSROOM_USER)
                    .where(CLASSROOM_USER.USER_ID.eq(userId))
                    .and(CLASSROOM_USER.CLASSROOM_ID.eq(classId))
        );
    }

    public List<ClassroomMember> getMembersFromClass(Integer classId) {
        return dsl.select(CLASSROOM_USER.USER_ID, USER.FIRST_NAME, USER.LAST_NAME, CLASSROOM_USER.ROLE)
                .from(CLASSROOM_USER)
                .join(USER)
                .on(USER.ID.eq(CLASSROOM_USER.USER_ID))
                .and(CLASSROOM_USER.CLASSROOM_ID.eq(classId))
                .fetch()
                .map(record -> ClassroomMember.newBuilder()
                        .setId(record.get(CLASSROOM_USER.USER_ID))
                        .setBasicInfo(
                                BasicInfo.newBuilder()
                                        .setFirstName(record.get(USER.FIRST_NAME))
                                        .setLastName(record.get(USER.LAST_NAME))
                                        .build()
                        )
                        .setRole(record.get(CLASSROOM_USER.ROLE))
                        .build());
    }

    public void deleteMemberFromClass(Integer memberId, Integer classId) {
        dsl.deleteFrom(CLASSROOM_USER)
                .where(CLASSROOM_USER.USER_ID.eq(memberId))
                .and(CLASSROOM_USER.CLASSROOM_ID.eq(classId))
                .execute();
    }

    public void updateClassDetails(Integer classId, String className, String classDesc, Boolean archived) {
        var fieldsToUpdate = new HashMap<>();
        if (className != null) {
            fieldsToUpdate.put(CLASSROOM.NAME, className);
        }
        if (classDesc != null) {
            fieldsToUpdate.put(CLASSROOM.DESCRIPTION, classDesc);
        }
        if (archived != null) {
            fieldsToUpdate.put(CLASSROOM.ARCHIVED, (byte) (archived ? 1 : 0));
        }
        dsl.update(CLASSROOM)
                .set(fieldsToUpdate)
                .where(CLASSROOM.ID.eq(classId))
                .execute();
    }

    public void deleteClassById(Integer classId) {
        dsl.deleteFrom(CLASSROOM)
                .where(CLASSROOM.ID.eq(classId))
                .execute();
    }

    public void updateMemberRole(Integer userId, Integer classId, String newRole) {
        dsl.update(CLASSROOM_USER)
                .set(CLASSROOM_USER.ROLE, newRole)
                .where(CLASSROOM_USER.USER_ID.eq(userId))
                .and(CLASSROOM_USER.CLASSROOM_ID.eq(classId))
                .execute();
    }

    public Integer createAnnouncement(Integer classId,
                                           Integer authorId,
                                           String title,
                                           String content,
                                           String sent_at) {
        return dsl.insertInto(ANNOUNCEMENT)
            .set(ANNOUNCEMENT.CLASSROOM_ID, classId)
            .set(ANNOUNCEMENT.USER_ID, authorId)
            .set(ANNOUNCEMENT.TITLE, title)
            .set(ANNOUNCEMENT.MESSAGE, content)
            .set(ANNOUNCEMENT.SENT_AT, DbUtils.getDateTime(sent_at))
            .returning(ANNOUNCEMENT.ID)
            .fetchOne(ANNOUNCEMENT.ID);
    }

    public List<Announcement> getAnnouncementsInClass(Integer classId) {
        return dsl.selectFrom(ANNOUNCEMENT)
            .where(ANNOUNCEMENT.CLASSROOM_ID.eq(classId))
            .fetch()
            .map(DbMapper::map);
    }

    @NotFoundOnNull(message = "Announcement not found")
    public Announcement getAnnouncement(Integer announcementId) {
        var rec = dsl.selectFrom(ANNOUNCEMENT)
            .where(ANNOUNCEMENT.ID.eq(announcementId))
            .fetchOneInto(ANNOUNCEMENT);
        return rec != null ? DbMapper.map(rec) : null;
    }

    public void deleteAnnouncementById(Integer announcementId) {
        dsl.deleteFrom(ANNOUNCEMENT)
            .where(ANNOUNCEMENT.ID.eq(announcementId))
            .execute();
    }

    public void updateAnnouncement(Integer announcementId, String title, String content) {
        var fieldsToUpdate = new HashMap<>();
        if (title != null) {
            fieldsToUpdate.put(ANNOUNCEMENT.TITLE, title);
        }
        if (content != null) {
            fieldsToUpdate.put(ANNOUNCEMENT.MESSAGE, content);
        }

        dsl.update(ANNOUNCEMENT)
            .set(fieldsToUpdate)
            .where(ANNOUNCEMENT.ID.eq(announcementId))
            .execute();
    }

    public boolean checkAnnouncementInClass(Integer announcementId, Integer classId) {
        return dsl.fetchExists(
            dsl.selectOne()
                .from(ANNOUNCEMENT)
                .where(ANNOUNCEMENT.ID.eq(announcementId))
                .and(ANNOUNCEMENT.CLASSROOM_ID.eq(classId))
        );
    }

}
