package com.grantvictor.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.hipchat.HipchatConstants;

/**
 * Created by ryan.tracy on 5/17/2017.
 */
public class HipChatPreProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Message out = exchange.getOut();
        out.setHeader(HipchatConstants.TO_ROOM, "Midway%20Dev%20Internal");
        out.setHeader(HipchatConstants.MESSAGE_FORMAT, "html");
        out.setHeader(HipchatConstants.MESSAGE_BACKGROUND_COLOR, "green");
        //out.setHeader(HipchatConstants.TRIGGER_NOTIFY, true); //use @here instead?
        out.setBody("@here Hello world3");
    }

}
