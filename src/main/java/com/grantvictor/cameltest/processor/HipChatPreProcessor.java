package com.grantvictor.cameltest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.hipchat.HipchatConstants;

import java.time.LocalDateTime;

/**
 * Created by ryan.tracy on 5/17/2017.
 */
public class HipChatPreProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Message out = exchange.getOut();
        out.setHeader(HipchatConstants.TO_ROOM, "Midway%20Dev%20Internal");
        out.setHeader(HipchatConstants.MESSAGE_FORMAT, "html");

        //out.setHeader(HipchatConstants.TRIGGER_NOTIFY, true); //use @here instead?

        boolean notify = false;

        JobDetail jobDetail = new JobDetail();

        jobDetail.setName("KORE_TEST_USAGE");
        jobDetail.setPeriod("24");
        jobDetail.setDate(LocalDateTime.now());
        jobDetail.setCarrierName("Kore");
        int successCount = 102;
        int errorCount = 7;
        int timeoutCount = 0;
        Long time = new Long(4L);

//        jobDetail.setName("VERIZON_TEST_USAGE");
//        jobDetail.setPeriod("48");
//        jobDetail.setDate(LocalDateTime.now());
//        jobDetail.setCarrierName("Verizon");
//        successCount = 43502;
//        errorCount = 1682;
//        timeoutCount = 9;
//        time = new Long(96L);


//        jobDetail.setName("ATTJASPER_TEST_USAGE");
//        jobDetail.setPeriod("72");
//        jobDetail.setDate(LocalDateTime.now());
//        jobDetail.setCarrierName("ATTJasper");
//        successCount = 48;
//        errorCount = 3;
//        timeoutCount = 1;
//        time = new Long(2L);

        if (timeoutCount > 50) {
            out.setHeader(HipchatConstants.MESSAGE_BACKGROUND_COLOR, "red");
            notify = true;
        } else if (timeoutCount > 0) {
            if (isLastUsageCollection(jobDetail.getCarrierName(), jobDetail.getPeriod())) {
                notify = true;
                out.setHeader(HipchatConstants.MESSAGE_BACKGROUND_COLOR, "red");
            } else {
                out.setHeader(HipchatConstants.MESSAGE_BACKGROUND_COLOR, "yellow");
            }
        } else {
            out.setHeader(HipchatConstants.MESSAGE_BACKGROUND_COLOR, "green");
        }
        out.setBody(getReportHTML(jobDetail, successCount+"", errorCount+"", timeoutCount+"", time, notify));
    }

    private String getReportHTML(JobDetail jobDetail, String successCount, String errorCount, String timeoutCount, Long totalTime, boolean notify) {
        StringBuilder rval = new StringBuilder();
        rval.append("<table><tr><th colspan=\"2\">Test Notification Report - IGNORE").append(notify ? " @here " : "").append("</th></tr>")
                .append("<tr><td>Job Name</td><td>").append(jobDetail.getName()).append("</td></tr>")
                .append("<tr><td>Job Period</td><td>").append(jobDetail.getPeriod()).append("</td></tr>")
                .append("<tr><td>Date to be Processed</td><td>").append(jobDetail.getDate().toLocalDate()).append("</td></tr>")
                .append("<tr><td>Devices with success</td><td>").append(successCount != null ? successCount : "0").append("</td></tr>")
                .append("<tr><td>Devices Timed Out</td><td>").append(timeoutCount != null ? timeoutCount : "0").append("</td></tr>")
                .append("<tr><td>Devices with error</td><td>").append(errorCount != null ? errorCount : "0").append("</td></tr>")
                .append("<tr><td>Total Time</td><td>").append(totalTime).append(" minutes</td></tr>")
                .append("</table>");

        return rval.toString();
    }

    private boolean isLastUsageCollection(String carrierName, String period) {
        boolean is72HourVerATTCollection = ("VERIZON".equalsIgnoreCase(carrierName) || "ATTJASPER".equalsIgnoreCase(carrierName)) && "72".equals(period);
        boolean is120HourKoreCollection = "KORE".equalsIgnoreCase(carrierName) && "120".equals(period);
        return is72HourVerATTCollection || is120HourKoreCollection;

    }

    class JobDetail {
        private String name;
        private String period;
        private String carrierName;
        private LocalDateTime date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public String getCarrierName() {
            return carrierName;
        }

        public void setCarrierName(String carrierName) {
            this.carrierName = carrierName;
        }
    }

}
