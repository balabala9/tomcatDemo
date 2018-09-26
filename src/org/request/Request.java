package org.request;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private  InputStream inputStream;
    private BufferedInputStream bufferedInputStream;
    private Map<String,String> headMap=new HashMap<>();
    private String uri;
    private String requestLine;
    private String method;
    private String protocolVersion;



    public Request(InputStream inputStream){
        this.inputStream=inputStream;
        requestHandle();

    }


    private void requestHandle(){

       StringBuffer request=new StringBuffer();
       int i;
       byte[] buffer=new byte[2048];

       try {
           i=inputStream.read(buffer);
       } catch (IOException e) {
           e.printStackTrace();
           i=-1;
       }
       for (int j=0;j<i;j++){
           request.append((char)buffer[j]);
       }

       String requestStr=request.toString();

       System.out.println("请求参数:"+request.toString());


        //处理请求行
        handleRequestLine(requestStr);
        //请求头处理
        handleRequestHead(requestStr);




    }

    private void handleRequestLine(String request){

        int indexRequestLine=request.indexOf("\r\n");

        if(indexRequestLine!=-1){
            requestLine=request.substring(0,indexRequestLine);
        }
        //请求方法
        String[] requestLineArr=requestLine.split(" ");
        method=requestLineArr[0];
        uri=requestLineArr[1];
        protocolVersion=requestLineArr[2];

    }

    private void handleRequestHead(String request){
        int firstIndex=request.indexOf("\r\n");
        int lastIndex=request.lastIndexOf("\r\n");

        String headBody=request.substring(firstIndex+2,lastIndex);

        System.out.println("headBody:"+headBody);

        String[] headBodyArr=headBody.split("\r\n");

        for(int i=0;i<headBodyArr.length;i++){
            int index=headBodyArr[i].indexOf(":");

            String key=headBodyArr[i].substring(0,index);
            String value=headBodyArr[i].substring(index+1);
            headMap.put(key,value);

        }
    }

    public Object getProperty(String k){
        if(headMap!=null && headMap.containsKey(k)){
            return headMap.get(k);
        }else {
            return null;
        }
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
}
