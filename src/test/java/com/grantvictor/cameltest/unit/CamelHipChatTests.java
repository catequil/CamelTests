package com.grantvictor.cameltest.unit;

import com.grantvictor.cameltest.processor.*;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Created by ryan.tracy on 2/27/2017.
 */
public class CamelHipChatTests extends CamelTestSupport {

        @Override
        public boolean isUseAdviceWith() {
            return true;
        }

        @Test
        public void test() throws Exception {
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

                    from("direct:myRoute")
                            .process(new HipChatPreProcessor())
                            .to("hipchat://?protocol=http&authToken=TSVOtuIpJkDBENc5zaQBRnoAQTRqmpmW6exspXha");
                }
            };
        }

}
