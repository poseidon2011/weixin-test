package com.example.weixintest.bean;

/**
 * @Author Lee Qian
 * @Description
 * @Date 2018/11/28
 */
public class WxButton {

    private String type;
    private String name;
    private WxButton[] sub_button;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public WxButton[] getSub_button() {
        return sub_button;
    }
    public void setSub_button(WxButton[] sub_button) {
        this.sub_button = sub_button;
    }

}
