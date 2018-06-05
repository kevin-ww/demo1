package com.codenotfound.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Receiver  {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    public static final int COUNT = 20;

    private CountDownLatch latch = new CountDownLatch(COUNT);

    public CountDownLatch getLatch() {
        return latch;
    }

//    @KafkaListener(id = "batch-listener", topics = "${kafka.topic.batch}")
    public void receive(List<String> data,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        LOGGER.info("[kevin] of batch receive");
        for (int i = 0; i < data.size(); i++) {
            LOGGER.info("received message='{}' with partition-offset='{}'", data.get(i),
                    partitions.get(i) + "-" + offsets.get(i));
            // handle message

            log(data.get(i));

            latch.countDown();
        }
        LOGGER.info("[kevin] end of batch receive");
    }


    protected void log(String data){
        System.out.println("data is "+data);
    }

//    @KafkaListener(topics = "testKafka")
//    public void receive(ConsumerRecord<?, ?> consumerRecord,
//                        Acknowledgment acknowledgment) {
//
//        System.out.println("Received message: ");
//        System.out.println(consumerRecord.value().toString());
//
//        acknowledgment.acknowledge();
//    }


}
