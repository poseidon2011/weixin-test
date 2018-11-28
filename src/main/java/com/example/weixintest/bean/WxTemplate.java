package com.example.weixintest.bean;

import lombok.Data;

import java.util.List;

/**
 * @Author Lee Qian
 * @Description 微信模版需要的数据
 * @Date 2018/11/28
 */
@Data
public class WxTemplate {

   // 消息接收方  

   private String toUser;

   // 模板id  

    private String templateId;

    // 模板消息详情链接  

    private String url;

    // 消息顶部的颜色  

    private String topColor;
    private List<WxTemplateParam> data; //消息具体内容


    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopColor() {
        return topColor;
    }

    public void setTopColor(String topColor) {
        this.topColor = topColor;
    }

    public List<WxTemplateParam> getData() {
        return data;
    }
    public void setData(List<WxTemplateParam> data) {
        this.data = data;
    }


    //封装成需要的json数据

public String toJSON() {

StringBuffer buffer= new StringBuffer();

buffer.append("{");

buffer.append(String.format("\"touser\":\"%s\"", this.toUser)).append(",");

buffer.append(String.format("\"template_id\":\"%s\"", this.templateId)).append(",");

buffer.append(String.format("\"url\":\"%s\"", this.url)).append(",");

buffer.append(String.format("\"topcolor\":\"%s\"", this.topColor)).append(",");

buffer.append("\"data\":{");

WxTemplateParam param= null;

for(int i= 0; i< this.data.size(); i++) {

param = data.get(i);

// 判断是否追加逗号  

if(i< this.data.size() - 1){


buffer.append(String.format("\"%s\": {\"value\":\"%s\",\"color\":\"%s\"},", param.getName(), param.getValue(), param.getColor()));

    }else{

    buffer.append(String.format("\"%s\": {\"value\":\"%s\",\"color\":\"%s\"}", param.getName(), param.getValue(), param.getColor()));

    }



    }

    buffer.append("}");

    buffer.append("}");

    return buffer.toString();

}



}
