package com.grantvictor.cameltest.unit;

public class RoutableMessage {
    private String route;
    private String header;
    private String body;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "\nRoutableMessage *************************** \nRoute: " + route + "\nHeader: " + header + "\nBody: " + body + "\n";
    }
}
