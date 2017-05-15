package com.grantvictor.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by ryan.tracy on 5/15/2017.
 */
public class PrintMessageBodyProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        System.out.println(body);
    }
}
