package com.grantvictor.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan.tracy on 5/16/2017.
 */
public class AddPropertiesProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        List<String> trees = new ArrayList<>();
        trees.add("Elm");
        trees.add("Cherry");
        trees.add("Burch");
        trees.add("Aspen");
        trees.add("Maple");
        trees.add("Fir");
        trees.add("Apple");
        trees.add("Peach");
        trees.add("Oak");
        trees.add("Spruce");
        exchange.setProperty("trees", trees);
    }

}
