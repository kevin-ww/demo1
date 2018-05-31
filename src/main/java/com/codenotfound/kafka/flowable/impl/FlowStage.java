package com.codenotfound.kafka.flowable.impl;

import com.codenotfound.kafka.flowable.Event;
import com.codenotfound.kafka.flowable.Stage;
import com.codenotfound.kafka.flowable.exceptions.StageException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.util.UUID;

/**
 * Created by kevin on 2018/5/30.
 */
@Component
public abstract class FlowStage implements Stage{


    public static final String PROCESS_SUCCESS="S";


    public static final String PROCESS_FAIL="F";

    public static final String CORRELATION_ID="CORRELATION_ID";


    public static final String EVENT_ID="EVENT_ID";


    public static final String statementPattern = "INSERT INTO EVENTS (correlationId,stage,headers,throwable) VALUES " +
            "('%s' ,'%s'," +
            "'%s'," +
            "'%s')";



    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void beforeProcess(Event event) throws StageException{

    }

    @Transactional
    public abstract void customProcess(Event event) throws StageException;

    @Override
    public void process(Event event) throws StageException{

        persistentEvent(event);

        customProcess(event);

    }

    @Override
    public void afterProcess(Event event) throws StageException{

    }


    protected void persistentEvent(Event event) throws StageException{
        //
        try {

            String correlationId = event.getHeader(CORRELATION_ID);

            if(StringUtils.isEmpty(correlationId)){
                correlationId= UUID.randomUUID().toString();
            }

            final String sql = String.format(statementPattern, correlationId ,this.getClass().getName(),event
                    .getHeaders().toString(),null);

            int eventId = insertAndGetAutoIncreaseId(sql);

            event.setHeader(EVENT_ID,String.valueOf(eventId));

            event.setHeader(CORRELATION_ID,correlationId);

        } catch (Exception e) {
            final StageStatus stageStatus = new StageStatus(this.getClass().getName(), PROCESS_FAIL, e);
            throw new StageException(stageStatus.toString());
        }
    }


    public <T> int insertAndGetAutoIncreaseId(final String sql) {

//        final String sql = "INSERT  INTO order_worker_quote_detail (work_quote_id, item_id, quantity, remarks) VALUES ('1','2','120','测试费用名')";
////                BeanOperator.getSqlByObject(SqlTypes.INSERT, obj);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            return ps;
        }, keyHolder);

        int autoIncId = keyHolder.getKey().intValue();

        return autoIncId;
    }



    @Data
    @AllArgsConstructor
    class StageStatus{
        String stageName;
        String status;
        Throwable exception;
    }

}
