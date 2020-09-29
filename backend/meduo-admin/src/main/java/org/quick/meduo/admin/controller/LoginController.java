/*
 * Copyright (c) 2019 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quick.meduo.admin.controller;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.quick.meduo.admin.audit.AuditHelper;
import org.quick.meduo.admin.model.UserModel;
import org.quick.meduo.admin.security.JwtAuthenticatioToken;
import org.quick.meduo.admin.service.UserService;
import org.quick.meduo.admin.utils.KaptchaUtils;
import org.quick.meduo.admin.utils.PasswordUtils;
import org.quick.meduo.admin.utils.SecurityUtils;
import org.quick.meduo.admin.utils.UserUtils;
import org.quick.meduo.admin.vo.LoginBean;
import org.quick.meduo.common.constants.UserState;
import org.quick.meduo.common.constants.UserType;
import org.quick.meduo.common.utils.IOUtils;
import org.quick.meduo.common.utils.StringUtils;
import org.quick.meduo.contacts.AuditTypeDefinition;
import org.quick.meduo.core.constant.ApiConstants;
import org.quick.meduo.core.constant.ErrorConstants;
import org.quick.meduo.core.http.AppStatus;
import org.quick.meduo.core.http.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
    @Autowired
    private Producer producer;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    /**
     * Request to generate vilidation code
     * @param response
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到验证码到Session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * User login interface
     * @param loginBean
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/auth/login")
    public Result login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();
        String email = loginBean.getEmail();
        String captcha = loginBean.getCaptcha();

        //Check if kaptcha is required and it is corrected. first check it and not let database overrun
        if(KaptchaUtils.isRequired(request)){
            Object kaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if(kaptcha == null){
                return Result.error(ErrorConstants.ERROR_KAPTCHA_EXPIRED);
            }
            if(!captcha.equals(kaptcha)){
                return Result.error(ErrorConstants.ERROR_KAPTCHA_INVILID);
            }
        }

        //Query User by using UID,here it is username
        LambdaQueryWrapper<UserModel> query = new LambdaQueryWrapper<>();
        if("email".equalsIgnoreCase(loginBean.getLoginType())){
            query.eq(UserModel::getEmail,email);
        }else{
            query.eq(UserModel::getUid,username);
        }

        UserModel user = userService.getOne(query);
        boolean isSystemInitial = false;
        //Account not exist
        if (user == null) {
            if(StringUtils.equals(ApiConstants.INITIAL_USER_UID,username) && StringUtils.equals(ApiConstants.INITIAL_USER_PASSWORD,password)){
                //first time to initial system account
                String salt = PasswordUtils.getSalt();
                String passwd = PasswordUtils.encode(ApiConstants.INITIAL_USER_PASSWORD, salt);
                UserModel u = new UserModel();
                u.setUid(ApiConstants.INITIAL_USER_UID);
                u.setPassword(passwd);
                u.setSalt(salt);
                u.setType(UserType.ADMIN);
                u.setState(UserState.NORMAL);
                u.setName(ApiConstants.INITIAL_USER_NAME);
                userService.saveOrUpdate(u);
                isSystemInitial = true;

                //re-query user info for this case,and reuse the logic
                LambdaQueryWrapper<UserModel> q = new LambdaQueryWrapper<>();
                query.eq(UserModel::getUid,username);
                user = userService.getOne(query);
            }else {
                return Result.error(ErrorConstants.ERROR_ACCOUNT_NOT_EXIST);
            }
        }

        // Check Account state
        if(!UserUtils.isAllowed(user)){
            //Record audit log
            AuditHelper.save(AuditTypeDefinition.error,username,user.getName(),request.getRemoteAddr(),"用户认证",ApiConstants.AUDIT_FAILD_TO_LOGIN,"禁止");
            return Result.error(ErrorConstants.ERROR_ACCOUNT_EXCEPTION);
        }

        //Password mismatch
        if (!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
            return Result.error(ErrorConstants.ERROR_ACCOUNT_PASSWD_MISMATCH);
        }

        // Login in system
        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);

        //Record audit log
        AuditHelper.save(AuditTypeDefinition.info,username,user.getName(),request.getRemoteAddr(),"用户认证",ApiConstants.AUDIT_OK_TO_LOGIN,"允许");

        if(isSystemInitial){
            return Result.ok( AppStatus.APC_SYSTEM_INTIAL, ApiConstants.SYSTEM_INITIAL_PROMPT, token );
        }
        return Result.ok(ApiConstants.OK_TO_LOGGIN,token);
    }

    /**
     * User logout interface
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/auth/logout")
    public Result logout(HttpServletRequest request) throws IOException {
        //Remove crypt key and notify all others？？TODO
        //        AuthContract contact = new AuthContract();
        //        contact.setUserName("admin");
        //        contact.setAccessToken(request.getHeader("access-token"));
        //        busAdaptor.publish("auth.logout",contact);

        AuditHelper.save(AuditTypeDefinition.info,UserUtils.currentUserId(),"",request.getRemoteAddr(),"用户认证","登出系统","允许");

        return Result.ok(ApiConstants.OK_TO_LOGOUT);
    }
}
