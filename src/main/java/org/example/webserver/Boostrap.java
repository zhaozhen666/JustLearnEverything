package org.example.webserver;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Boostrap {

    public static final Integer port =9090;
    public static void main(String[] args) throws IOException {
        Boostrap boostrap = new Boostrap();
        boostrap.loadServletConfig();
        boostrap.start();
    }


    private Map<String,HttpServlet> servletMap = new HashMap<>();
    private  void loadServletConfig(){
        InputStream servletConfig = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(servletConfig);
            Element rootElement =document.getRootElement();
            List<Element> selectNodes = rootElement.selectNodes("servlet");
            for (Element element:selectNodes){
                Element servletNameElement =(Element) element.selectSingleNode("servlet-name");
                String servletName = servletNameElement.getStringValue();

                Element servletClassElement =(Element) element.selectSingleNode("servlet-class");
                String servletClass = servletClassElement.getStringValue();

//                Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");

                Element servletMapping = (Element)rootElement.selectSingleNode("servlet-mapping[servlet-name='"+servletName+"']");
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                System.out.println("url-pattern:"+urlPattern+"=========servletName:"+servletName+"=======servletClass:"+servletClass);
                servletMap.put(urlPattern,(HttpServlet) Class.forName(servletClass).newInstance());
            }


        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private  void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("MyTomcat starting ");
        while (true){
            Socket accept = serverSocket.accept();
//            InputStream inputStream =  accept.getInputStream();
//            int count =0;
//            while (count ==0){
//                count =inputStream.available();
//            }
//            byte[] bytes = new byte[count];
//            inputStream.read(bytes);
//            System.out.println("请求信息:"+new String(bytes));
//            Request request = new Request(inputStream);
////            OutputStream outputStream =accept.getOutputStream();
//            Response response = new Response(accept.getOutputStream());
//            if (servletMap.get(request.getUrl())==null){
//                response.outputHtml(request.getUrl());
//            }else {
//                HttpServlet httpServlet = servletMap.get(request.getUrl());
//                httpServlet.service(request,response);
//            }

            RequestProcessor requestProcessor = new RequestProcessor(accept,servletMap);
//            requestProcessor.start();
            int corePoolSize =10;
            int maxPoolSize =50;
            Long keepAliveTime = 100L;
            TimeUnit timeUnit = TimeUnit.SECONDS;
            BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
            ThreadFactory threadFactory =Executors.defaultThreadFactory();
            RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maxPoolSize,keepAliveTime,
                    timeUnit,workQueue,threadFactory,rejectedExecutionHandler);
            threadPoolExecutor.execute(requestProcessor);




//            String responseText = "Hello world MyTomcat";
//            String responseContent = MyHttpResponse.http200Response(responseText.getBytes().length)+responseText;
//            outputStream.write(responseContent.getBytes());

        }
    }
}
