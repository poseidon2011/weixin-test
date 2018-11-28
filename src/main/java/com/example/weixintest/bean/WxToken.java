package com.example.weixintest.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Lee Qian
 * @Description 凭证
 * @Date 2018/11/28
 */
@Data
public class WxToken implements Serializable {

    // 接口访问凭证
    private String accessToken;
    // 凭证有效期，单位：秒
    private int expiresIn;

}
