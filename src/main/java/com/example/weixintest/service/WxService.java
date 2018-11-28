package com.example.weixintest.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.weixintest.constant.WxConstants;
import com.example.weixintest.util.ConnectUtil;
import com.example.weixintest.util.MenuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @Author Lee Qian
 * @Description
 * @Date 2018/11/28
 */
@Component
public class WxService {
    private static final Logger logger = LoggerFactory.getLogger(WxService.class);

    @Scheduled(fixedRate= 1000*60*90, initialDelay = 2000)//项目启动2秒中之后执行一次，然后每90min执行一次，单位都为ms
    public  String getToken(){

        String url = WxConstants.BASE_TOKEN_URL + "grant_type="+WxConstants.GRANT_TYPE+"&appid="+WxConstants.APP_ID+"&secret="+WxConstants.SECRET;
        try{
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            JSONObject jsonObject = JSON.parseObject(result);
            logger.info("access_token:"+jsonObject.getString("access_token"));
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            logger.error("获取token失败！"+e.toString());
            return "";
        }
    }

    /**
     * 发送模板消息
     *
     * 接口访问凭证
     * @param jsonMsg json格式的模板消息
     * @return true | false
     */
    public  void sendModelMessage(String jsonMsg) {
        try {
        logger.info("消息内容：{}", jsonMsg);
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", getToken());


        // 打开和URL之间的连接
        String result = ConnectUtil.post(requestUrl,jsonMsg);
        // 发送客服消息
        JSONObject jsonObject = JSON.parseObject(result);

        if (null != jsonObject) {
            int errorCode = jsonObject.getInteger("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                logger.info("模板消息发送成功 errcode:{} errmsg:{}", errorCode, errorMsg);
            } else {
                logger.error("模板消息发送失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        } catch (Exception e){
            logger.error("出现异常:"+e.toString());
        }

    }
    /**
     * @Author wxt.leeq
     * @Description
     * @Date 2018/11/28
     */
    public void createMenu(String jsonMsg) {
        MenuUtil.createMenu(jsonMsg,getToken());
    }
}
