package com.grantvictor.cameltest.router;

import com.grantvictor.cameltest.exception.AnotherException;
import com.grantvictor.cameltest.exception.YetAnotherException;
import com.grantvictor.cameltest.processor.MyProcessor;
import org.apache.camel.builder.RouteBuilder;

import java.util.logging.Logger;

/**
 * Created by ryan.tracy on 2/27/2017.
 */
public class CamelRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(CamelRoute.class.getName());

    @Override
    public void configure() throws Exception {

    }
}
