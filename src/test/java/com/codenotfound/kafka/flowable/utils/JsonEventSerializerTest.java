package com.codenotfound.kafka.flowable.utils;

import com.codenotfound.kafka.flowable.Event;
import com.codenotfound.kafka.flowable.mock.AssetChangeRequest;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by kevin on 05/06/2018.
 */
public class JsonEventSerializerTest {
    @Test
    public void to() throws Exception {

        final JsonEventSerializer jsonEventSerializer = new JsonEventSerializer();

        AssetChangeRequest assetChangeRequest
                = new AssetChangeRequest();
        assetChangeRequest.setUser("kevin");
        assetChangeRequest.setOrg("mag");
        assetChangeRequest.setSymbol("uat");
        assetChangeRequest.setAmount(new BigDecimal(100.99));

        final Event event = new Event(assetChangeRequest);

        event.setHeader("header1","val1");
        event.setHeader("header2","val2");

        event.setEventId(999L);

        final String to = jsonEventSerializer.to(event);

        System.out.println(to);


        final Event from = jsonEventSerializer.from(to);

        System.out.println(from);


    }

    @Test
    public void from() throws Exception {

    }

}