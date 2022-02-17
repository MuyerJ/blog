package com.muyer.controller;

import com.muyer.common.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Describe: 注册获得手机验证码
 */
@RestController
@Slf4j
public class GetPhoneCodeController {


    private static final String REGISTER = "register";

    /**
     * 阿里云 accessKeyId
     */
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;

    /**
     * 阿里云 secret
     */
    @Value("${aliyun.secret}")
    private String secret;

    /**
     * 阿里云短信发送模板
     */
    private static final String SIGN_NAME = "张海洋ocean";

    @PostMapping(value = "/getCode", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getAuthCode(@RequestParam("phone") String phone,
                              @RequestParam("sign") String sign){

        String trueMsgCode = "";
        for(int i=0;i<4;i++){
            trueMsgCode += Math.round(Math.random() * 9);
        }

       //在redis中保存手机号验证码并设置过期时间
//        stringRedisService.set(phone, trueMsgCode);
//        stringRedisService.expire(phone, 300);

        String msgCode;
        //注册的短信模板
        if(REGISTER.equals(sign)){
            msgCode = "SMS_136394413";
        }
        //改密码的短信模板
        else {
            msgCode = "SMS_139982667";
        }

        try {
            sendSmsResponse(phone, trueMsgCode, msgCode);
        } catch (Exception e) {
            log.error("[{}] send phone message exception", phone, e);
            return JsonResult.fail().toJSON();
        }

        return JsonResult.success().toJSON();
    }

    private void sendSmsResponse(String phoneNumber, String code, String msgCode) throws Exception {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //"***"分别填写自己的AccessKey ID和Secret
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);
//        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
//        IAcsClient acsClient = new DefaultAcsClient(profile);
//        SendSmsRequest request = new SendSmsRequest();
//        //填写接收方的手机号码
//        request.setPhoneNumbers(phoneNumber);
//        //此处填写已申请的短信签名
//        request.setSignName(SIGN_NAME);
//        //此处填写获得的短信模版CODE
//        request.setTemplateCode(msgCode);
//        //笔者的短信模版中有${code}, 因此此处对应填写验证码
//        request.setTemplateParam("{\"code\":\"" + code + "\"}");
//        acsClient.getAcsResponse(request);

    }



}
