package com.codenotfound.kafka.flowable.mock;

import com.codenotfound.kafka.flowable.utils.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by kevin on 04/06/2018.
 */
@Service
public class UserService {


    @Autowired
    JdbcTemplate jdbcTemplate;
//
//
//    @Autowired
//    RestTemplate restTemplate;


    public int createUser(String name, String org) throws DataAccessException {

        String userSql = String.format("INSERT INTO USER (USER,ORG) VALUES ('%s','%s')", name, org);

        return JdbcUtils.insertAndGetAutoIncreasedId(jdbcTemplate, userSql);

    }

//    @Transactional
//    public int saveAssetInMysql(User payload) throws Exception {
//
//        String name = payload.getName();
//
//        String org = payload.getOrg();
//
//        String userSql = String.format("INSERT INTO USER (NAME,ORG) VALUES ('%s','%s')", name, org);
//
//        int userId = JdbcUtils.insertAndGetAutoIncreasedId(jdbcTemplate, userSql);
//
//        String orgSql = String.format("INSERT INTO ORG (ORG,USER) VALUES ('%s','%s')", org, userId);
//
//        int orgId = JdbcUtils.insertAndGetAutoIncreasedId(jdbcTemplate, orgSql);
//
//        return userId;
//
//    }


//    public void saveAssetInFabric(User payload) throws Exception {
//
//
//        //
//        final String url = "http://www.facebook.com";
//
//        //TODO ,exception handle;
//        final String response = restTemplate.getForObject(url, String.class);
//
//
//    }

}
