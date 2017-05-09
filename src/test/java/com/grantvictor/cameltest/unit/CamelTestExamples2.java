package com.grantvictor.cameltest.unit;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class CamelTestExamples2 extends CamelTestSupport {

    @Test
    public void test() throws Exception {

        Collection<RoutableMessage> routableMessages = new ArrayList<>();
        RoutableMessage routableMessage = new RoutableMessage();
        routableMessage.setRoute("direct:north");
        routableMessage.setHeader("northHeader");
        routableMessage.setBody("northBody");
        routableMessages.add(routableMessage);

        routableMessage = new RoutableMessage();
        routableMessage.setRoute("direct:east");
        routableMessage.setHeader("eastHeader");
        routableMessage.setBody("eastBody");
        routableMessages.add(routableMessage);

        routableMessage = new RoutableMessage();
        routableMessage.setRoute("direct:south");
        routableMessage.setHeader("southHeader");
        routableMessage.setBody("southBody");
        routableMessages.add(routableMessage);

        routableMessage = new RoutableMessage();
        routableMessage.setRoute("direct:west");
        routableMessage.setHeader("westHeader");
        routableMessage.setBody("westBody");
        routableMessages.add(routableMessage);

        template.request("direct:collection", (Exchange exchange) -> {
            exchange.getIn().setBody(routableMessages);
            System.out.println("*** *** *** toString() " + exchange.getFromEndpoint().toString());
            System.out.println("*** *** *** getEndpointUri() " + exchange.getFromEndpoint().getEndpointUri());
        });
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("direct:collection")
                        .split(body()).recipientList(bodyAs(RoutableMessage.class).method("route"));

                from("direct:north").to("log:input");

                from("direct:east").to("log:input");

                from("direct:south").to("log:input");

                from("direct:west").to("log:input?showHeaders=true");
            }
        };
    }


}


