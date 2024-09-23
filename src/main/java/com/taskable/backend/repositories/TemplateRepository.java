package com.taskable.backend.repositories;

import com.taskable.backend.exception_handling.NotFoundOnNull;
import com.taskable.backend.utils.DbMapper;
import com.taskable.protobufs.PersistenceProto.Template;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

import static com.taskable.jooq.tables.Classroom.CLASSROOM;
import static com.taskable.jooq.tables.Template.TEMPLATE;

@Repository
public class TemplateRepository {

    private final DSLContext dsl;

    @Autowired
    public TemplateRepository(DSLContext dsl) {this.dsl = dsl;}

    public List<Template> getTemplatesInClass(Integer classId) {
        return dsl.selectFrom(TEMPLATE)
                .where(TEMPLATE.CLASSROOM_ID.eq(classId))
                .fetchInto(TEMPLATE)
                .map(DbMapper::map);
    }

    @NotFoundOnNull(message = "Template not found")
    public Template getTemplateById(Integer templateId) {
        var rec = dsl.selectFrom(TEMPLATE)
                .where(TEMPLATE.ID.eq(templateId))
                .fetchOneInto(TEMPLATE);
        return rec != null ? DbMapper.map(rec) : null;
    }

    public Integer createTemplate(String name) {
        return dsl.insertInto(TEMPLATE)
                .set(TEMPLATE.NAME, name)
                .returning(TEMPLATE.ID)
                .fetchOneInto(Integer.class);
    }

    public void updateTemplateDetails(Integer templateId, String name, String description, Boolean archived) {
        var fieldsToUpdate = new HashMap<>();
        if (name != null) {
            fieldsToUpdate.put(TEMPLATE.NAME, name);
        }
        if (description != null) {
            fieldsToUpdate.put(TEMPLATE.DESCRIPTION, description);
        }
        if (archived != null) {
            fieldsToUpdate.put(TEMPLATE.ARCHIVED, (byte) (archived ? 1 : 0));
        }
        dsl.update(TEMPLATE)
                .set(fieldsToUpdate)
                .where(TEMPLATE.ID.eq(templateId))
                .execute();
    }

    public void deleteTemplateById(Integer id) {
        dsl.deleteFrom(TEMPLATE)
                .where(TEMPLATE.ID.eq(id))
                .execute();
    }
}
