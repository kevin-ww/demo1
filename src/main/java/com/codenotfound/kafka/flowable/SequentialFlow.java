package com.codenotfound.kafka.flowable;

import com.codenotfound.kafka.flowable.exceptions.FlowException;
import com.codenotfound.kafka.flowable.exceptions.StageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;


public class SequentialFlow implements Flow {

    protected static final Logger LOGGER = LoggerFactory.getLogger(SequentialFlow.class);

    private String name = "default";

    private LinkedList<Stage> stages;


    public SequentialFlow(String name, LinkedList<Stage> stages) {
        this.name = name;
        this.stages = stages;
    }


    public void startWith(Event event) throws FlowException {

        for (Stage stage : stages) {
            try {
                LOGGER.info("now processing stage {}", stage.getName());
                stage.process(event);
            } catch (StageException e) {
                throw new FlowException(e);
            }

        }

    }

    @Override
    public String toString() {
        return "SequentialFlow{" +
                "name='" + name + '\'' +
                ", stages=" + stages +
                '}';
    }
}
