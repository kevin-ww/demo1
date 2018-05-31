package com.codenotfound.kafka.flowable;

import com.codenotfound.kafka.flowable.exceptions.StageException;

import java.util.LinkedList;

/**
 * Created by kevin on 31/05/2018.
 */
public class SequentialFlow implements Flow {


    private String name;

    private LinkedList<Stage> stages;


    public SequentialFlow(String name, LinkedList<Stage> stages) {
        this.name = name;
        this.stages = stages;
    }


    public void startWith(Event event) throws StageException {

        for(Stage stage:stages){
            stage.process(event);
        }

    }
}
