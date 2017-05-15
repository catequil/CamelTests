package com.grantvictor.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by ryan.tracy on 5/8/2017.
 */
public class ExternalTimeoutProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setHeader("da82fba6-0602-418a-9799-ce51d523da87","");
        exchange.getIn().setHeader(Exchange.ACCEPT_CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_METHOD, "GET");
        exchange.getIn().setHeader(Exchange.HTTP_PATH, "api/v1/timeout");
        exchange.getIn().setHeader(Exchange.HTTP_QUERY, "timeMillis=60000");
    }
}
