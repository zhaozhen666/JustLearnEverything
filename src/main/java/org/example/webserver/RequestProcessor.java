package org.example.webserver;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

@AllArgsConstructor
public class RequestProcessor extends Thread{


    private Socket socket;
    private Map<String,HttpServlet> servletMap;


    @SneakyThrows
    @Override
    public void run() {
        InputStream inputStream =  socket.getInputStream();
//            int count =0;
//            while (count ==0){
//                count =inputStream.available();
//            }
//            byte[] bytes = new byte[count];
//            inputStream.read(bytes);
//            System.out.println("请求信息:"+new String(bytes));
        Request request = new Request(inputStream);
//            OutputStream outputStream =accept.getOutputStream();
        Response response = new Response(socket.getOutputStream());
        if (servletMap.get(request.getUrl())==null){
            response.outputHtml(request.getUrl());
        }else {
            HttpServlet httpServlet = servletMap.get(request.getUrl());
            httpServlet.service(request,response);
        }
        socket.close();
    }
}
