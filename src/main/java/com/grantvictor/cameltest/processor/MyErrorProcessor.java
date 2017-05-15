package com.grantvictor.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.CxfOperationException;

/**
 * Created by ryan.tracy on 5/15/2017.
 */
public class MyErrorProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        final Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
        System.out.println(exception.getMessage());
    }
}
