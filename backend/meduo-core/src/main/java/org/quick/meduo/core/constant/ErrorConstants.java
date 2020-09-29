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

package org.quick.meduo.core.constant;

/**
 * Description:
 * Changelog:
 * Authors: Brian.G
 * 2019-11--11
 */
public class ErrorConstants {
    /*后端错误提示定义*/
    public static final String UNKOWN_ERROR="未知异常,请联系管理员";
    public static final String ERROR_KAPTCHA_EXPIRED="验证码已失效";
    public static final String ERROR_KAPTCHA_INVILID="验证码不正确";
    public static final String ERROR_ACCOUNT_NOT_EXIST="账号不存在";
    public static final String ERROR_ACCOUNT_DUP="账号已占用";
    public static final String ERROR_ACCOUNT_PASSWD_MISMATCH="密码不正确";
    public static final String ERROR_ACCOUNT_EXCEPTION="账号异常或已被锁定,请联系管理员";
    public static final String ERROR_NOT_ALLOWED="不被允许的操作,请退出系统";
    public static final String ERROR_FE_ERROR="前端参数传递错误";
    public static final String ERROR_OLD_PASSWORD_MISMATCH="原密码输入错误";
}
