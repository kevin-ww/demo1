package com.codenotfound.kafka.flowable.utils;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

@Component
public class JdbcUtils {


    public static int insertAndGetAutoIncreasedId(JdbcTemplate jdbcTemplate, final String sql) {

        //check not null for both jdbcTemplate and sql statement;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            return ps;
        }, keyHolder);

        int autoIncId = keyHolder.getKey().intValue();

        return autoIncId;
    }


    public static void execute(JdbcTemplate jdbcTemplate,final String sql){
        jdbcTemplate.execute(sql);
    }

}
