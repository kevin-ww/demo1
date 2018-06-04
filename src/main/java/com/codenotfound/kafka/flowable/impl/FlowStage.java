package com.codenotfound.kafka.flowable.impl;

import com.codenotfound.kafka.flowable.Event;
import com.codenotfound.kafka.flowable.Stage;
import com.codenotfound.kafka.flowable.exceptions.StageException;
import com.codenotfound.kafka.flowable.utils.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Component
public abstract class FlowStage implements Stage<Event> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(FlowStage.class);


//    public static final String PROCESS_SUCCESS = "S";


//    public static final String PROCESS_FAIL = "F";

    public static final String CORRELATION_ID = "CORRELATION_ID";


//    public static final String EVENT_ID = "EVENT_ID";


    public static final String statementPattern =
            "INSERT INTO EVENTS " +
                    "(correlationId,stage,headers,throwable) " +
                    "VALUES " +
                    "('%s' ," +
                    "'%s'," +
                    "'%s'," +
                    "'%s')";

    private JdbcTemplate jdbcTemplate;


    public FlowStage(){
        //

    }

    public FlowStage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


//    @Override
//    public void setName(String name) {
//        this.name="flowStage";
//    }

    @Override
    public String getName() {
        return "flowStage";
    }

    @Override
    public void beforeProcess(Event event) throws StageException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("event {} before this stage", event);
        }
    }

    @Transactional
    public abstract void customProcess(Event event) throws Exception;


    protected void internalProcess(Event event) throws StageException{
        logEvent(event);




        try {
            //interceptor1
            beforeProcess(event);
            //call custom process
            customProcess(event);
            //interceptor2
            afterProcess(event);
        } catch (Exception e) {

            //if failed , log the exception into event tbl;
            if (LOGGER.isInfoEnabled()) { //TODO;
                logEvent(event, e);
            }

            throw new StageException(e);
        }

    }

    @Override
    public void process(Event event) throws StageException {
        internalProcess(event);

    }

    @Override
    public void afterProcess(Event event) throws StageException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("event {} after this stage", event);
        }
    }


    protected void logEvent(Event e) throws StageException {
        logEvent(e, null);
    }


    protected void logEvent(Event event, Throwable t) throws StageException {
        //
        try {

            String correlationId = event.getHeader(CORRELATION_ID);

            final String sql = String.format(statementPattern, correlationId, this.getName(), event
                    .getHeaders().toString(), t);

            int eventId = JdbcUtils.insertAndGetAutoIncreasedId(jdbcTemplate, sql);

            if (StringUtils.isEmpty(correlationId)) {
                event.setHeader(CORRELATION_ID, String.valueOf(eventId));
            }


        } catch (Exception e) {
            throw new StageException("exception occurred when doing persistent event", e);
        }
    }


    @Override
    public String toString() {
        return "Stage{" +
                "name=" + getName() +
                '}';
    }
}
