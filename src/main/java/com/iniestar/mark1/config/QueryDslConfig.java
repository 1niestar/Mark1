package com.iniestar.mark1.config;

import com.iniestar.mark1.code.DatabaseType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLTemplates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;


@Configuration
public class QueryDslConfig {
    private final String database;
    private final EntityManager entityManager;

    QueryDslConfig(@Value("${spring.jpa.database}") String database, EntityManager entityManager) {
        this.database = database;
        this.entityManager = entityManager;
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public SQLTemplates sqlTemplates() {
       return DatabaseType.from(database).getSQLTemplates();
    }
}
