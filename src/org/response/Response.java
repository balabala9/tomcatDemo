package org.response;

import org.request.Request;
import org.server.HttpServer;

import java.io.*;


public class Response {

    private OutputStream outputStream;

    public Response(OutputStream outputStream){
        this.outputStream=outputStream;
    }

    public void sendStaticResource(Request request) throws IOException {

        byte[] buffer=new byte[1024];
        FileInputStream fileInputStream=null;

        try {
            File file=new File(HttpServer.WEB_ROOT,request.getUri());
            System.out.println("文件地址:"+file.getAbsolutePath());

            StringBuffer stringBuffer=new StringBuffer();

            if(file.exists()){
                fileInputStream=new FileInputStream(file);

                int ch=fileInputStream.read(buffer,0,1024);
                while (ch!=-1){
                    stringBuffer.append(new String(buffer));
                    ch=fileInputStream.read(buffer,0,1024);
                }

                int length=stringBuffer.length();
                String successMessage="HTTP/1.1 200 Ok \r\n" +
                        "Content-Type:text/html\r\n" +
                        "Content-Length:"+length+"\r\n" +
                        "\r\n" +
                        stringBuffer;
                outputStream.write(successMessage.getBytes());

            }else {
                String errorMessage="HTTP/1.1 400 File No Found \r\n" +
                        "Content-Type:text/html\r\n"+
                        "Content-Length:23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                outputStream.write(errorMessage.getBytes());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream!=null){
                fileInputStream.close();
            }

        }
    }
}
