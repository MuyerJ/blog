package com.muyer.controller;

import com.muyer.common.utils.JsonResult;
import com.muyer.common.utils.MD5Util;
import com.muyer.model.CodeType;
import com.muyer.model.User;
import com.muyer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Describe: 登录控制
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/changePassword", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String changePassword(@RequestParam("phone") String phone,
                                 @RequestParam("authCode") String authCode,
                                 @RequestParam("newPassword") String newPassword){

        try {
            String trueMsgCode = "";
//            String trueMsgCode = (String) stringRedisService.get(phone);

            //判断获得的手机号是否是发送验证码的手机号
            if(trueMsgCode == null){
                return JsonResult.fail(CodeType.PHONE_ERROR).toJSON();
            }
            //判断验证码是否正确
            if(!authCode.equals(trueMsgCode)){
                return JsonResult.fail(CodeType.AUTH_CODE_ERROR).toJSON();
            }
            User user = userService.findUserByPhone(phone);
            if(user == null){
                return JsonResult.fail(CodeType.USERNAME_NOT_EXIST).toJSON();
            }
            MD5Util md5Util = new MD5Util();
            String mD5Password = md5Util.encode(newPassword);
            userService.updatePasswordByPhone(phone, mD5Password);

            //修改密码成功删除redis中的验证码
//            stringRedisService.remove(phone);

            return JsonResult.success().toJSON();
        } catch (Exception e){
            log.error("[{}] change password [{}] exception", phone, newPassword, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}
