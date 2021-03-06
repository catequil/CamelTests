package com.grantvictor.cameltest.unit;

import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Created by ryan.tracy on 10/14/2016.
 */
public class CamelTestExamples extends CamelTestSupport {

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void test() throws Exception {
        RouteDefinition route = context.getRouteDefinition("myRouteMiddle");
        route.adviceWith(context, new AdviceWithRouteBuilder() {

            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("direct:end")
                        //.process((Exchange exchange) -> {
                        //System.out.println(exchange.getIn().getBody(String.class));
                        //})
                        .to("mock:direct:end")
                        .skipSendToOriginalEndpoint();
            }
        });

        context.start();

        template.request("direct:start", (Exchange exchange) -> {
            exchange.getIn().setBody("hello world!");
        });

        MockEndpoint mock = getMockEndpoint("mock:direct:end");

        mock.expectedMessageCount(1);
        mock.expectedBodyReceived().body().isEqualTo("hello world!");

        mock.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("direct:start").routeId("myRouteStart")
                        .to("log:input")
                        .to("direct:middle");

                from("direct:middle").routeId("myRouteMiddle")
                        .process((Exchange exchange) -> {
                            exchange.getIn().setBody("peace world!");
                        })
                        .to("log:input")
                        .to("direct:end");

                from("direct:end").routeId("myRouteEnd")
                        .process((Exchange exchange) -> {
                            exchange.getIn().setBody("bye world!");
                        })
                        .to("log:input");
            }
        };
    }

}
