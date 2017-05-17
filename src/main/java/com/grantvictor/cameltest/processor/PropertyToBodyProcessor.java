package com.grantvictor.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by ryan.tracy on 5/16/2017.
 */
public class PropertyToBodyProcessor implements Processor {

    private String _propName;

    public PropertyToBodyProcessor(String propertyName) {
        _propName = propertyName;
    }


    @Override
    public void process(Exchange exchange) throws Exception {
        Object value = exchange.getProperty(_propName);
        if (value != null) {
            exchange.getIn().setBody(value);
        } else {
            throw new IllegalArgumentException("Unknown property " + _propName);
        }
    }
}
