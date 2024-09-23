package com.taskable.backend.repositories;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateRepository {

    private final DSLContext dsl;

    @Autowired
    public TemplateRepository(DSLContext dsl) {this.dsl = dsl;}
}
