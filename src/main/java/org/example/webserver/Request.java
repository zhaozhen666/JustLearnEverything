package org.example.webserver;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;

@Data
public class Request {

    private String method;

    private String url;

    private InputStream inputStream;

    public Request(InputStream inputStream) throws IOException {
        int count =0;
        while (count ==0){
            count =inputStream.available();
        }
        byte[] bytes = new byte[count];
        inputStream.read(bytes);
        String reqStr = new String(bytes,"UTF-8");
        String[]   lines = reqStr.split("\n");
        if (lines.length>0){
            String[]  firstLine = lines[0].split(" ");
            this.method=firstLine[0];
            this.url=firstLine[1];
            System.out.println("方法:"+this.method+"资源地址:"+this.url);

        }
    }
}
