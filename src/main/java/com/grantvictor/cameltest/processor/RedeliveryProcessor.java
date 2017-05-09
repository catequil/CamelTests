package com.grantvictor.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by ryan.tracy on 5/1/2017.
 */
public class RedeliveryProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Integer redeliveryCount = exchange.getProperty("redeliveryCount", Integer.class);
        if (redeliveryCount != null) {
            exchange.setProperty("redeliveryCount", redeliveryCount + 1);
        } else {
            exchange.setProperty("redeliveryCount", 1);
        }
    }
}
