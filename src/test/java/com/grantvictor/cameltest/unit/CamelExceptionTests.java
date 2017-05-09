package com.grantvictor.cameltest.unit;

import com.grantvictor.cameltest.exception.AnotherException;
import com.grantvictor.cameltest.exception.YetAnotherException;
import com.grantvictor.cameltest.processor.MyProcessor;
import com.grantvictor.cameltest.processor.RedeliveryProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Created by ryan.tracy on 2/27/2017.
 */
public class CamelExceptionTests extends CamelTestSupport {

        @Override
        public boolean isUseAdviceWith() {
            return true;
        }

        @Test
        public void test() throws Exception {

            context.start();

            template.request("direct:myRoute", (Exchange exchange) -> {
                exchange.getIn().setBody("YetAnotherException");
            });
        }

        @Override
        protected RouteBuilder createRouteBuilder() throws Exception {
            return new RouteBuilder() {

                @Override
                public void configure() throws Exception {

                    onException(YetAnotherException.class)
                            .handled(true)
                            .onRedelivery(new RedeliveryProcessor())
                            .maximumRedeliveries(2)
                            .log(LoggingLevel.WARN, "***   YetAnotherException happened.");

                    from("direct:myRoute")
                            .doTry()
                            .to("direct:anotherRoute")
                            .doCatch(AnotherException.class)
                            .log(LoggingLevel.WARN, "***   AnotherException happened.")
                            .end()
                            .log("myRoute done!");

                    from("direct:anotherRoute")
                            .errorHandler(noErrorHandler())
                            .to("direct:myProcessorRoute")
                            //.process(new MyProcessor())
                            .log("...   anotherRoute done!");

                    from("direct:myProcessorRoute")
                            .process(new MyProcessor());

                }
            };
        }

}
