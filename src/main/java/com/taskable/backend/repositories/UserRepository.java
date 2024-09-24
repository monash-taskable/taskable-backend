package com.taskable.backend.repositories;

import com.taskable.backend.exception_handling.NotFoundOnNull;
import com.taskable.backend.utils.DbMapper;
import com.taskable.jooq.tables.records.UserRecord;
import com.taskable.protobufs.PersistenceProto.UserSettings;
import com.taskable.protobufs.PersistenceProto.BasicInfo;
import com.taskable.protobufs.PersistenceProto.User;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taskable.jooq.tables.User.USER;

@Repository
public class UserRepository {

    private final DSLContext dsl;

    @Autowired
    public UserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Integer getUserIdBySub(String sub) {
        return dsl.select(USER.ID)
               .from(USER)
               .where(USER.SUB.eq(sub))
               .fetchOne(USER.ID);
    }

    public Map<String, Integer> getUserIdsByEmails(List<String> emails) {
        Map<String, Integer> result = new HashMap<>();
        int batchSize = 500;

        for (int i = 0; i < emails.size(); i += batchSize) {
            List<String> batch = emails.subList(i, Math.min(i + batchSize, emails.size()));
            result.putAll(
                    dsl.select(USER.EMAIL, USER.ID)
                            .from(USER)
                            .where(USER.EMAIL.in(batch))
                            .fetchMap(USER.EMAIL, USER.ID)
            );
        }
        return result;
    }

    public Integer storeUser(User user, String sub) {
        return dsl.insertInto(USER)
                .set(USER.EMAIL, user.getBasicInfo().getEmail())
                .set(USER.SUB, sub)
                .set(USER.FIRST_NAME, user.getBasicInfo().getFirstName())
                .set(USER.LAST_NAME, user.getBasicInfo().getLastName())
                .set(USER.STATUS, user.getUserSettings().getStatus())
                .set(USER.LANGUAGE, user.getUserSettings().getLanguage())
                .set(USER.COLOUR, user.getUserSettings().getColor())
                .set(USER.THEME, user.getUserSettings().getTheme())
                .returning(USER.ID)
                .fetchOne(USER.ID);
    }

    @NotFoundOnNull(message = "Resource not found")
    public User getUserById(Integer id) {
        UserRecord res = dsl.select()
                .from(USER)
                .where(USER.ID.eq(id))
                .fetchOneInto(UserRecord.class);
        return res != null ? DbMapper.map(res) : null;
    }

    public void updateProfileById(Integer userId, String status, String language, String color, String theme) {
        var fieldsToUpdate = new HashMap<>();
        if (language != null) {
            fieldsToUpdate.put(USER.LANGUAGE, language);
        }
        if (color != null) {
            fieldsToUpdate.put(USER.COLOUR, color);
        }
        if (status != null) {
            fieldsToUpdate.put(USER.STATUS, status);
        }
        if (theme != null) {
            fieldsToUpdate.put(USER.THEME, theme);
        }
        dsl.update(USER)
                .set(fieldsToUpdate)
                .where(USER.ID.eq(userId))
                .execute();
    }
}
