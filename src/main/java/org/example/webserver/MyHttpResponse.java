package org.example.webserver;

public class MyHttpResponse {
    public static String http200Response(long contentLength){
        return "HTTP/1.1 200 OK \n"+
                "Content-Type: text/html; charset=utf-8 \n"+
                "Content-Length:"+contentLength+" \n"+
                "\r\n";
    }

    public static String http404Response(){
        String str404 = "<h1>404 NOT FOUND</h1>";
        return "HTTP/1.1 404 NOT FOUND \n"+
                "Content-Type: text/html; charset=utf-8 \n"+
                "Content-Length:"+str404.getBytes().length+" \n"+
                "\r\n"+str404;
    }
}
