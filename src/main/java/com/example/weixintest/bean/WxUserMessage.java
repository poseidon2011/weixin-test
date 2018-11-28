package com.example.weixintest.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Lee Qian
 * @Description 微信用户发送过来的消息
 * @Date 2018/11/28
 */
@Data
public class WxUserMessage implements Serializable {

    private String ToUserName;

    private String FromUserName;

    private Long CreateTime;

    private String MsgType;

    private String Content;

    private String MsgId;

}
