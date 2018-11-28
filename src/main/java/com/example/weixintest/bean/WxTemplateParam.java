package com.example.weixintest.bean;

import lombok.Data;

/**
 * @Author Lee Qian
 * @Description 微信模版需要的数据
 * @Date 2018/11/28
 */
@Data
public class WxTemplateParam {

    // 参数名称

    private String name;

// 参数值

    private String value;

// 颜色

    private String color;


    public WxTemplateParam(String name,String value,String color){
        this.name = name;
        this.value = value;
        this.color =color;
    }
}
