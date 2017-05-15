package com.grantvictor.cameltest.unit;

import com.grantvictor.cameltest.processor.*;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Created by ryan.tracy on 2/27/2017.
 */
public class CamelSedaTimeoutTests extends CamelTestSupport {

        @Override
        public boolean isUseAdviceWith() {
            return true;
        }

        @Test
        public void test() throws Exception {
            //cxfrs://http://mid2.gv.systems/MidWay/api/v1/timeout
//            CxfRsClient cxfEndpoint = new CxfEndpoint();
//            cxfEndpoint.setAddress("http://mid2.gv.systems/MidWay/api/v1/timeout");
//            cxfEndpoint.setCamelContext(context);
//            cxfEndpoint.setDataFormat(DataFormat.PAYLOAD);
//
//            try {
//                context.addEndpoint("cxfEndpoint", cxfEndpoint);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            context.start();

            template.request("direct:myRoute", (Exchange exchange) -> {
                exchange.getIn().setBody("Body");
            });
        }

        @Override
        protected RouteBuilder createRouteBuilder() throws Exception {
            return new RouteBuilder() {

                @Override
                public void configure() throws Exception {

                    onException(ExchangeTimedOutException.class)
                            .handled(true)
                            .log(LoggingLevel.ERROR, "Time Out Exception")
                            .process(new MyErrorProcessor())
                            .end();

                    from("direct:myRoute")
                            .process(new CreateListToProcess())
                            .split(body())
                            .parallelProcessing(true)
                            .to("seda:processItem");

                    from("seda:processItem?concurrentConsumers=2&timeout=45000")
                            .process(new PrintMessageBodyProcessor())
                            .choice()
                            .when(m -> m.getIn().getBody(String.class).equals("Elephant"))
                            .process(new ExternalTimeoutProcessor())
                            .to("cxfrs://http://mid2.gv.systems/MidWay/")
                            .end();

                }
            };
        }

}
