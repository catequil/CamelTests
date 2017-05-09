package com.grantvictor.cameltest.processor;

import com.grantvictor.cameltest.exception.AnException;
import com.grantvictor.cameltest.exception.AnotherException;
import com.grantvictor.cameltest.exception.YetAnotherException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by ryan.tracy on 2/27/2017.
 */
public class MyProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Integer redeliveredCount = exchange.getProperty("redeliveryCount", Integer.class);
        if (redeliveredCount != null) {
            System.out.println("--- redeliveryCount: " + redeliveredCount);
            if (redeliveredCount > 1) {
                throw new AnotherException();
            }
        }
        if (exchange.getIn().getBody().equals("AnotherException")) {
            throw new AnotherException();
        } else if (exchange.getIn().getBody().equals("YetAnotherException")) {
            throw new YetAnotherException();
        }
        throw new AnException();
    }
}
