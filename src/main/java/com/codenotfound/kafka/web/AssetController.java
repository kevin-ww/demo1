package com.codenotfound.kafka.web;

import com.codenotfound.kafka.flowable.exceptions.AssetChangeException;
import com.codenotfound.kafka.flowable.mock.AssetChangeRequest;
import com.codenotfound.kafka.flowable.mock.AssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kevin on 04/06/2018.
 */

@RestController
@RequestMapping("/asset")
public class AssetController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AssetController.class);

    @Autowired
    AssetService assetService;


    @RequestMapping(value = "/case1" , method = RequestMethod.POST)
    @ResponseBody
    public void changeAsset(@RequestBody AssetChangeRequest req) throws AssetChangeException {

        LOGGER.info("change asset based on request {}",req);

        //validate TODO;
//
//        String user  = req.getUser();
//        String org = req.getOrg();
//        String symbol = req.getSymbol();
//        BigDecimal amount = req.getAmount();
//
//        LOGGER.info("change asset for {} with symbol {} and amount {}",user,symbol,amount);

        assetService.changeAsset(req);


    }



}
