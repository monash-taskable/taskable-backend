package com.taskable.backend.repositories;

import com.taskable.backend.utils.DbMapper;
import com.taskable.jooq.tables.records.UserRecord;
import com.taskable.protobufs.PersistenceProto.User;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.taskable.jooq.tables.User.USER;

@Repository
public class UserRepository {

    private final DSLContext dsl;

    @Autowired
    public UserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Integer getUserIdBySub(String sub) {
         var rec = dsl.select()
                .from(USER)
                .where(USER.SUB.eq(sub))
                .fetchOneInto(UserRecord.class);
        return rec != null ? rec.getId() : null;
    }

    public Integer storeUser(User user) {
        UserRecord res = dsl.insertInto(USER)
                .set(USER.EMAIL, user.getEmail())
                .set(USER.SUB, user.getSub())
                .set(USER.FIRST_NAME, user.getFirstName())
                .set(USER.LAST_NAME, user.getLastName())
                .set(USER.STATUS, user.getStatus())
                .set(USER.LANGUAGE, user.getLanguage())
                .set(USER.COLOUR, user.getColor())
                .set(USER.THEME, user.getTheme())
                .returning(USER.ID)
                .fetchOne();
        return res.getId();
    }

    public User getUserById(Integer id) {
        UserRecord res = dsl.select()
                .from(USER)
                .where(USER.ID.eq(id))
                .fetchOneInto(UserRecord.class);
        return res != null ? DbMapper.map(res) : null;
    }
}
