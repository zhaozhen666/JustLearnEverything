package org.example.webserver;

public interface Servlet {

    public void  init(Request request,Response response);

    public void destroy(Request request,Response response);

    public void service(Request request,Response response);
}
