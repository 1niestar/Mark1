package com.iniestar.mark1.code;

import com.querydsl.sql.OracleTemplates;
import com.querydsl.sql.SQLTemplates;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum DatabaseType {
    ORACLE("oracle") {
        @Override
        public SQLTemplates getSQLTemplates() {
            return new OracleTemplates();
        }
    };

    String type;

    public abstract SQLTemplates getSQLTemplates();

    public static DatabaseType from(String database) {
        return Arrays.stream(DatabaseType.values()).filter(x -> x.getType().equals(database)).findFirst().orElse(null);
    }
}
