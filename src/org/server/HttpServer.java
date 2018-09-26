package org.server;

import org.request.Request;
import org.response.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private int port=8080;

    public static final  String WEB_ROOT=System.getProperty("user.dir")+ File.separator+"webRoot";

    private static final  String SHUTDOWN_COMMAND="/shutdown";

    /**
     * web服务器：请求－响应
     * 请求：
     * １.接收请求
     * 2.处理请求
     * 3.对请求做出响应的响应
     * 4.正常请求　错误请求和关闭指令
     * 响应：
     * 1.对请求对出分析查找对于资源
     * 2.对成功和失败响应都做出处理
     *
     */
    public HttpServer(){}

    public HttpServer(int port){
        this.port=port;
    }


    public void serverHandle(){

        ServerSocket serverSocket=null;

        boolean isShutdown=false;

        try {
            serverSocket=new ServerSocket(port);
            FileInputStream fileInputStream=null;
            byte[] buffer=new byte[1024];

            while (!isShutdown){
                Socket socket=serverSocket.accept();
                InputStream inputStream=socket.getInputStream();
                OutputStream outputStream=socket.getOutputStream();

                Request request=new Request(inputStream);

                Response response=new Response(outputStream);
                response.sendStaticResource(request);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        HttpServer httpServer=new HttpServer(8099);
        httpServer.serverHandle();
    }
}
