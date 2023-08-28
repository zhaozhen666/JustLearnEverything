package org.example.webserver;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

public class MyServlet extends HttpServlet{
    @Override
    public void doGet(Request request, Response response) {
        try {
            Thread.sleep(100000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String content ="<h1>MyServlet GET  Come on </h1>";
        try {
            response.output(MyHttpResponse.http200Response(content.length())+content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String content ="<h1>MyServlet POST  Come on </h1>";
        try {
            response.output(MyHttpResponse.http200Response(content.length())+content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(Request request, Response response) {

    }

    @Override
    public void destroy(Request request, Response response) {

    }
}
