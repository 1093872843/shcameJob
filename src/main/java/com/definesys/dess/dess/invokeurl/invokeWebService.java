package com.definesys.dess.dess.invokeurl;

import com.definesys.dess.dess.DessLog.bean.DessLogVO;
import com.definesys.dess.dess.util.CommonUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName invokeWebService
 * @Description TODO
 * @Author Xueyunlong
 * @Date 2020-8-4 17:08
 * @Version 1.0
 **/

public class invokeWebService {

    private String httpUrl;
    private String contentType;
    private String soapOperation;
    private String requestConent;
    private Map<String,String> headerPayload;
    public static final String HTTP_CONTENT_TYPE_JSON ="application/json; charset=UTF-8";
    public static final String HTTP_CONTENT_TYPE_XML ="application/xml; charset=UTF-8";




    public String doPost() throws IOException {
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            //服务的地址
            URL wsUrl = new URL(httpUrl);
            conn = (HttpURLConnection)wsUrl.openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(120000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            if(getContentType() != null) {
                conn.setRequestProperty("Content-Type",getContentType());
            }
            if (soapOperation != null && soapOperation.length() > 0) {
                conn.setRequestProperty("SOAPAction",soapOperation);
            }
            //用户自定义标头
            if(headerPayload != null && !headerPayload.isEmpty()){
                Iterator<String> keyIter = headerPayload.keySet().iterator();
                while(keyIter.hasNext()){
                    String headerName = keyIter.next();
                    String headerVaule =headerPayload.get(headerName);
                    if(headerVaule != null){
                        conn.setRequestProperty(headerName,headerVaule);
                    }
                }
            }
            os = conn.getOutputStream();
            if (getRequestConent() != null) {
                os.write(getRequestConent().getBytes());
            }
            is = conn.getInputStream();
            byte[] b = new byte[1024];
            int len = 0;
            String result = "";
            while ((len = is.read(b)) != -1) {
                String ss = new String(b,0,len,"UTF-8");
                result += ss;
            }
            return result;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public  String doGet() throws Exception {
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            //服务的地址
            URL wsUrl = new URL(httpUrl);
            conn = (HttpURLConnection)wsUrl.openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(120000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            if(contentType!= null) {
                conn.setRequestProperty("Content-Type",contentType);
            }
            //用户自定义标头
            if(headerPayload!= null && !headerPayload.isEmpty()){
                Iterator<String> keyIter = headerPayload.keySet().iterator();
                while(keyIter.hasNext()){
                    String headerName = keyIter.next();
                    String headerVaule = headerPayload.get(headerName);
                    if(headerVaule != null){
                        conn.setRequestProperty(headerName,headerVaule);
                    }
                }
            }
            is = conn.getInputStream();
            byte[] b = new byte[1024];
            int len = 0;
            String s = "";
            while ((len = is.read(b)) != -1) {
                String ss = new String(b,0,len,"UTF-8");
                s += ss;
            }
            return s;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }



    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }


    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSoapOperation() {
        return soapOperation;
    }

    public void setSoapOperation(String soapOperation) {
        this.soapOperation = soapOperation;
    }

    public String getRequestConent() {
        return requestConent;
    }

    public void setRequestConent(String requestConent) {
        this.requestConent = requestConent;
    }

    public Map<String, String> getHeaderPayload() {
        return headerPayload;
    }

    public void setHeaderPayload(Map<String, String> headerPayload) {
        this.headerPayload = headerPayload;
    }

    public final static void main(String[] args){

    }


}
