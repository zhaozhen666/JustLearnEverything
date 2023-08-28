package org.example.webserver;

import lombok.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Data
public class Response {

    private OutputStream outputStream;

    public Response(OutputStream outputStream){
        this.outputStream=outputStream;
    }


    public void output(String content) throws IOException {
        outputStream.write(content.getBytes());
    }
    public void outputHtml(String path) throws IOException {
        String absoluteResourcePath =ResourceUtil.getAbsolutePath(path);

        File file =new File(absoluteResourcePath);
        if (file.exists() && file.isFile()){
            ResourceUtil.outputStaticResource(new FileInputStream(file),outputStream);
        }else {
            output(MyHttpResponse.http404Response());
        }
    }
}
