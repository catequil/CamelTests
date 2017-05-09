package com.grantvictor.cameltest.unit;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan.tracy on 1/23/2017.
 */
public class CamelTestExamples3 extends CamelTestSupport {
    @Test
    public void test() throws Exception {
        List<Body> bodies = new ArrayList<>();
        bodies.add(new Body("body 1"));
        bodies.add(new Body("body 2"));



        template.request("direct:collection", (Exchange exchange) -> {
            exchange.getIn().setBody(bodies);
        });
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("direct:collection").setHeader("id").simple("1")
                        .split(body())
                        .to("direct:single")
                .aggregate(header("id"), new CombineBodiesAggregationStrategy())
                        .completionSize(2)
                        .to("log:input?showHeaders=true");

                from("direct:single")
                        .to("log:input?showHeaders=true");
            }
        };
    }

    private class CombineBodiesAggregationStrategy implements AggregationStrategy {

        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            System.out.println("********** Entered Aggregate");
            if (oldExchange != null) {
                Body oldBody = oldExchange.getIn().getBody(Body.class);
                Body newBody = newExchange.getIn().getBody(Body.class);
                newExchange.getIn().setBody(oldBody.value + " - " + newBody.value);
            }
            return newExchange;
        }

    }

    private class Body {
        String value;

        Body(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Body[" + value + "]";
        }
    }
}
