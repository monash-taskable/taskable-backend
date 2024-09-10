package com.taskable.backend.repositories;

import com.taskable.backend.controllers.AuthController;
import com.taskable.backend.utils.DbMapper;
import com.taskable.backend.utils.DbUtils;
import com.taskable.jooq.tables.records.ClassroomRecord;
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

        dsl.insertInto(CLASSROOM_USER)
                .set(CLASSROOM_USER.CLASSROOM_ID, res.getId())
                .set(CLASSROOM_USER.USER_ID, userId)
                .set(CLASSROOM_USER.ROLE, "OWNER")
                .execute();

        return DbMapper.map(res);
    }

    public List<Pair<Classroom, String>> getClassesRolesByUserId(Integer id) {
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
}
