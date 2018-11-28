package com.example.weixintest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.weixintest.bean.WxButton;
import com.example.weixintest.bean.WxClickButton;
import com.example.weixintest.bean.WxMenu;
import com.example.weixintest.bean.WxViewButton;
import com.example.weixintest.constant.WxConstants;

import java.io.UnsupportedEncodingException;

/**
 * @Author Lee Qian
 * @Description
 * @Date 2018/11/28
 */
public class MenuUtil {

    public static WxMenu initMenu(){
        WxMenu menu = new WxMenu();
        WxClickButton clickButton = new WxClickButton();
        clickButton.setKey("clickButton");
        clickButton.setType("click");
        clickButton.setName("clickMenu");

        WxViewButton viewButton = new WxViewButton();
        viewButton.setName("viewButton");
        viewButton.setType("view");
        viewButton.setUrl("http://www.baidu.com");

        WxClickButton clickButton1 = new WxClickButton();
        clickButton1.setKey("scanButton");
        clickButton1.setType("scancode_push");
        clickButton1.setName("scanButton");

        WxClickButton clickButton2 = new WxClickButton();
        clickButton2.setKey("locationButton");
        clickButton2.setType("location_select");
        clickButton2.setName("locationButton");

        WxButton button = new WxButton();
        button.setName("Menu");
        button.setSub_button(new WxButton[]{clickButton1,clickButton2});

        menu.setButton(new WxButton[]{clickButton,viewButton,button});
        return menu;
    }
    //创建菜单的url拼接
    public static int createMenu(String menu, String token) {

        String url = WxConstants.CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        int result = 0;
        String json = ConnectUtil.post(url, menu);
        JSONObject jsonObject = JSON.parseObject(json);
        if(jsonObject != null){
            result = jsonObject.getInteger("errcode");
        }
        return result;
    }
    //查询菜单的url的拼接
    public static JSONObject queryMenu(String token){
        String url = WxConstants.CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        String result = ConnectUtil.get(url);
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }
}
