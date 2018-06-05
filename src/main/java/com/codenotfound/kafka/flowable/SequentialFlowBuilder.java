package com.codenotfound.kafka.flowable;

import java.util.LinkedList;

public class SequentialFlowBuilder {

    private String name;

    private LinkedList<Stage> stages;

    public SequentialFlowBuilder setName(String name) {
        this.name = name;
        return this;
    }


    public SequentialFlowBuilder addStage(Stage stage) {
        if (this.stages == null) {
            this.stages = new LinkedList<>();
        }


        stages.addLast(stage);
        return this;
    }


    public SequentialFlow createSequentialFlow() {
        return new SequentialFlow(name, stages);
    }
}