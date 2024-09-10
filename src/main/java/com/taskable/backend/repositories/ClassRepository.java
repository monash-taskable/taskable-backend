package com.taskable.backend.repositories;

import com.google.common.collect.ImmutableList;
import com.taskable.backend.utils.DbMapper;
import com.taskable.backend.utils.DbUtils;
import com.taskable.protobufs.PersistenceProto.ClassroomMember;
import com.taskable.protobufs.PersistenceProto.Classroom;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
                .fetchOne();
        logger.info(res != null ? res.toString() : "storeClass record returned is null");

        addUserToClass(userId, res.getId(), "OWNER");

        return DbMapper.map(res);
    }

    public boolean addUserToClass(Integer userId, Integer classId, String role) {
        int status = dsl.insertInto(CLASSROOM_USER)
                .set(CLASSROOM_USER.CLASSROOM_ID, classId)
                .set(CLASSROOM_USER.USER_ID, userId)
                .set(CLASSROOM_USER.ROLE, role)
                .onDuplicateKeyIgnore()
                .execute();
        return status == 1;
    }

    public String getUserRoleInClass(Integer userId, Integer classId) {
        return dsl.select(CLASSROOM_USER.ROLE)
                .from(CLASSROOM_USER)
                .where(CLASSROOM_USER.USER_ID.eq(userId))
                .and(CLASSROOM_USER.CLASSROOM_ID.eq(classId))
                .fetchOneInto(String.class);
    }

    public List<Pair<Classroom, String>> getClassesAndRolesByUserId(Integer id) {
        var results = dsl.select(CLASSROOM.fields())
                .select(CLASSROOM_USER.ROLE)
                .from(CLASSROOM)
                .join(CLASSROOM_USER)
                .on(CLASSROOM.ID.eq(CLASSROOM_USER.CLASSROOM_ID))
                .where(CLASSROOM_USER.USER_ID.eq(id))
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
                dsl.selectFrom(CLASSROOM_USER)
                        .where(CLASSROOM_USER.USER_ID.eq(userId))
                        .and(CLASSROOM_USER.CLASSROOM_ID.eq(classId))
        );
    }

    public List<ClassroomMember> getMembersFromClass(Integer classId) {
        return dsl.select(CLASSROOM_USER.USER_ID, USER.FIRST_NAME, USER.LAST_NAME, CLASSROOM_USER.ROLE)
                .from(USER)
                .join(CLASSROOM_USER)
                .on(USER.ID.eq(CLASSROOM_USER.USER_ID))
                .where(CLASSROOM_USER.CLASSROOM_ID.eq(classId))
                .fetch()
                .map(record -> ClassroomMember.newBuilder()
                        .setId(record.get(CLASSROOM_USER.USER_ID))
                        .setFirstName(record.get(USER.FIRST_NAME))
                        .setLastName(record.get(USER.LAST_NAME))
                        .setRole(record.get(CLASSROOM_USER.ROLE))
                        .build());
    }

    public void deleteMemberFromClass(Integer memberId, Integer classId) {
        dsl.deleteFrom(CLASSROOM_USER)
                .where(CLASSROOM_USER.USER_ID.eq(memberId))
                .and(CLASSROOM_USER.CLASSROOM_ID.eq(classId))
                .execute();
    }

    public void updateMemberRole(Integer userId, Integer classId, String newRole) {
        dsl.update(CLASSROOM_USER)
                .set(CLASSROOM_USER.ROLE, newRole)
                .where(CLASSROOM_USER.USER_ID.eq(userId))
                .and(CLASSROOM_USER.CLASSROOM_ID.eq(classId))
                .execute();
    }

}
