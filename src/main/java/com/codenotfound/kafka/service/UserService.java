package com.codenotfound.kafka.service;

import com.google.common.eventbus.Subscribe;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

/**
 * Created by kevin on 2018/5/29.
 */

@Service
public class UserService {

    SequentialFlow simpleFlow;
//            = new SimpleFlow().addStage(stage1).addStage(stage2);


    public void registerUser(String name,String org) throws UserRegistrationException{

        simpleFlow.triggerBy(new UserRegistrationEvent(name,org));


    }




    class SequentialFlow {

        String name;

        LinkedList<Stage> stages;




        //
        public SequentialFlow build(){
            return this;
        }

        public SequentialFlow addStage(Stage stage){
            return this;
        }

        public void triggerBy(BasicEvent event){

        }

    }

    class Stage{

        Processor processor;






    }


    interface Processor<IN, OUT>{

        OUT process(IN in);

    }

    class UserRegiestrationDBProcessor implements Processor<UserRegistrationEvent,UserRegistrationEvent>{
        @Override
        @Subscribe
        public UserRegistrationEvent process(UserRegistrationEvent userRegistrationEvent) {
            return null;
        }

    }

    class UserRegiestrationFabricProcessor implements Processor<UserRegistrationEvent,UserRegistrationEvent>{

        @Override
        @Subscribe
        public UserRegistrationEvent process(UserRegistrationEvent userRegistrationEvent) {
            return null;
        }
    }


    class UserRegistrationException extends Exception{

    }


    @Data
    class UserRegistrationEvent extends BasicEvent {
        String name;
        String org;

        UserRegistrationEvent(String name,String org){
            this.name=name;
            this.org=org;
        }

    }

    @Data
    class BasicEvent{
        long updated;
        String updatedBy;
        int version;
        State state;

    }

    enum  State {

        STARTED,
        SUCCEED,
        FAILED,
        UNKNOWN,

    }
}
