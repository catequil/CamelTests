package com.grantvictor.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan.tracy on 5/15/2017.
 */
public class CreateListToProcess implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        List<String> list = new ArrayList<>();
        list.add("Turtle");
        list.add("Lion");
        list.add("Gorilla");
        list.add("Sloth");
        list.add("Giraffe");
        list.add("Penguin");
        list.add("Macaw");
        list.add("Alligator");
        list.add("Camel");
        list.add("Wildebeest");
        list.add("Cheetah");
        list.add("Elephant");
        list.add("Hippo");
        list.add("Rhino");
        exchange.getIn().setBody(list);
    }
}
