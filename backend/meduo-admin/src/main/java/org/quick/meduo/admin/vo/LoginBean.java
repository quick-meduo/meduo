package org.quick.meduo.admin.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginBean {
    private String username;
    private String password;
    private String loginType;
    private String email;
    private String captcha;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLoginType() {
        return loginType;
    }

    public String getEmail() {
        return email;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}