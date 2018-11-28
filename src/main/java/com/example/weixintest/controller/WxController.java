package com.example.weixintest.controller;

import com.alibaba.fastjson.JSON;
import com.example.weixintest.bean.*;
import com.example.weixintest.constant.WxConstants;
import com.example.weixintest.service.WxService;
import com.example.weixintest.util.CheckoutUtil;
import com.example.weixintest.util.MessgaeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Lee Qian
 * @Description 主要测试类
 * @Date 2018/11/28
 */
@Controller
@RequestMapping("wx")
public class WxController {
    private static final Logger logger = LoggerFactory.getLogger(WxController.class);
    @RequestMapping("/")
    @ResponseBody
    private String index(){
        return "Hello Wx Test";
    }


    /**
     * @Author wxt.leeq
     * @Description 响应微信发送的Token验证
     * @Date 2018/11/28
     */
    @GetMapping("/check")
    private void checkToken(HttpServletRequest request, HttpServletResponse response) {
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter print;
        if (isGet) {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
                try {
                    print = response.getWriter();
                    print.write(echostr);
                    print.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Resource
    private WxService wxService;
    /**
     * @Author wxt.leeq
     * @Description 获取access_token 信息
     * @Date 2018/11/28
     */
    @RequestMapping("/getToken")
    @ResponseBody
    private String getToken() {
        return wxService.getToken();
    }

    /**
     * @Author wxt.leeq
     * @Description 推送消息模板到微信公众号
     * @Date 2018/11/28
     */
    @RequestMapping("/temple")
    @ResponseBody
    private void sendTemple(){
        List<WxTemplateParam> list = new ArrayList<WxTemplateParam>();
        list.add(new WxTemplateParam("first", "统计时间：2018-11-27", "#173177"));
        list.add(new WxTemplateParam("keyword1", "李前", "#173177"));
        list.add(new WxTemplateParam("keyword2", "住院号：18111094 床位号：1", "#173177"));
        list.add(new WxTemplateParam("keyword3", "2606.1508", "#173177"));
        list.add(new WxTemplateParam("keyword4", "-1606.1508", "#173177"));
        list.add(new WxTemplateParam("keyword5", "检查费：23  床位费：13  接生费：23  检验费：43  护理费：17  总费用:197", "#EE0000"));
        list.add(new WxTemplateParam("remark", "祝您身体健康，生活愉快", "#173177"));
        WxTemplate tem= new WxTemplate();
        //接收人的openId
        tem.setToUser("o9qqz0gOu7vLqXVpIvNFzMD8qSgA");
        //消息模板Id
        tem.setTemplateId("miShPU8Hd9ZjyFNm_1_pBsTpIgxs6wGAODD9HberlPA");
        //消息打开跳转网页链接
        tem.setUrl("http://weixin.qq.com/");
        tem.setData(list);
        wxService.sendModelMessage(tem.toJSON());

    }

    /**
     * @Author wxt.leeq
     * @Description 收到微信公众号发送过来的消息并回复
     * @Date 2018/11/28
     */
    @PostMapping("/check")
    @ResponseBody
    private void messageReply(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String str = null;
        response.setCharacterEncoding("UTF-8");

        //将request请求，传到Message工具类的转换方法中，返回接收到的Map对象
        try {
            PrintWriter out = response.getWriter();
            Map<String, String> map = MessgaeUtil.xmlToMap(request);

            //从集合中，获取XML各个节点的内容
            String ToUserName = map.get("ToUserName");
            String FromUserName = map.get("FromUserName");
            String CreateTime = map.get("CreateTime");
            String MsgType = map.get("MsgType");
            String Content = map.get("Content");
            String MsgId = map.get("MsgId");
            //这里只处理文本消息
            if (MsgType.equalsIgnoreCase(WxConstants.REQ_MESSAGE_TYPE_TEXT)) {
                WxUserMessage message = new WxUserMessage();
                message.setFromUserName(ToUserName);
                message.setToUserName(FromUserName);
                message.setContent("您发送的消息是text文本消息" + "很帅");
                message.setMsgId(MsgId);
                message.setMsgType("text");
                message.setCreateTime(new Date().getTime());
                str = MessgaeUtil.objectToXml(message);
            }
            // 事件推送(当用户主动点击菜单，或者扫面二维码等事件)
            else if (MsgType.equalsIgnoreCase(WxConstants.REQ_MESSAGE_TYPE_EVENT)) {
                // 关注
                if (MsgType.equalsIgnoreCase(WxConstants.EVENT_TYPE_SUBSCRIBE)){
                    logger.info("有一位用户关注了该公众号");
                }
            }

            //System.out.println(str);
            out.print(str);
            out.close();


        }catch (Exception e){
            logger.error("出现异常:"+str);
        }
    }

    /**
     * @Author wxt.leeq
     * @Description 微信公众号创建菜单
     * @Date 2018/11/28
     */
    @GetMapping("/menu")
    @ResponseBody
    private void createMenu(){
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
        String fromObject =  JSON.toJSONString(menu);
        wxService.createMenu(fromObject);
    }




}
