package com.taskable.backend.repositories;

import com.taskable.backend.utils.DbMapper;
import com.taskable.protobufs.PersistenceProto.Template;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.taskable.jooq.tables.Template.TEMPLATE;

@Repository
public class TemplateRepository {

    private final DSLContext dsl;

    @Autowired
    public TemplateRepository(DSLContext dsl) {this.dsl = dsl;}

    public List<Template> getTemplatesInClass(Integer classId) {
        return dsl.select(TEMPLATE.fields())
                .from(TEMPLATE)
                .where(TEMPLATE.CLASSROOM_ID.eq(classId))
                .fetchInto(TEMPLATE)
                .map(DbMapper::map);
    }
}
