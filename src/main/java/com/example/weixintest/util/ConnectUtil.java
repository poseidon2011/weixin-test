package com.example.weixintest.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * @Author Lee Qian
 * @Description 网络请求封装
 * @Date 2018/11/28
 */
public class ConnectUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConnectUtil.class);

    public static String post(String url, String data) {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";
        try {
            StringEntity s= new StringEntity(data, "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);
            // 发送请求
            HttpResponse httpResponse = client.execute(post);
            // 获取响应输入流
            InputStream inStream= httpResponse.getEntity().getContent();
            BufferedReader reader= new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber= new StringBuilder();
            String line= null;
            while ((line = reader.readLine()) != null)
            strber.append(line+ "\n");
            inStream.close();
            result = strber.toString();

            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                logger.info("请求服务器成功，做相应处理");

            } else{
                logger.info("请求服务端失败");
            }

        } catch (Exception e) {
            logger.info("请求异常:"+e.toString());
        }

        return result;

    }


    public static String get(String url) {
        String result = "";
        try {
            URL realUrl = new URL(url);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();

        connection.setRequestMethod("GET");

        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        } catch (ProtocolException e) {
            logger.error("出现异常:"+e.toString());
        } catch (MalformedURLException e) {
            logger.error("出现异常:"+e.toString());
        } catch (IOException e) {
            logger.error("出现异常:"+e.toString());
        }
        return result;
    }
}
