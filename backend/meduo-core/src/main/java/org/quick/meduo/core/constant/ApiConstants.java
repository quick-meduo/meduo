package org.quick.meduo.core.constant;

/**
 *
 */
public class ApiConstants {
    /*系统初始化相关*/
    public static final String INITIAL_USER_UID="meduo";
    public static final String INITIAL_USER_PASSWORD="229073436db97f71c091c4de8f982f1d"; //plain: @meduo
    public static final String INITIAL_USER_NAME="初始用户";
    public static final String SYSTEM_INITIAL_PROMPT="系统初始化登录,请及时修改密码";
    /*---end---*/

    /*图像码相关提示*/
    public static final String KAPTCHA_SESSION_REQUIRED_KEY="KAPTCHA_SESSION_REQUIRED";
    public static final String KAPTCHA_SESSION_REQUIRED="required";
    /*---end---*/
    /*登录相关提示*/
    public static final String OK_TO_LOGGIN="成功登录系统,欢迎使用!";
    public static final String OK_TO_LOGOUT="成功登出系统!";
    /*---end---*/

    /*审计相关提示*/
    public static final String AUDIT_OK_TO_LOGIN="登录系统,操作成功!";
    public static final String AUDIT_FAILD_TO_LOGIN="尝试登录系统,凭证验证不通过!";
    public static final String AUDIT_FAILD_UPDATE_USER="尝试修改用户,不允许的操作!";
    public static final String AUDIT_FAILD_CREATE_SERVICE="尝试创建服务,不允许的操作!";
    public static final String AUDIT_FAILD_UPDATE_SERVICE="尝试修改服务,不允许的操作!";
    /*---end---*/

    /*用户操作相关提示*/
    public static final String OK_TO_ADD_USER="成功添加用户!";
    public static final String OK_TO_UPDATE_USER="成功修改用户!";
    public static final String OK_TO_CREATE_SERVICE="成功创建服务!";
    public static final String OK_TO_CREATE_PERMGROUP="成功创建授权组!";
    public static final String OK_TO_CREATE_FEATURE_GROUP="成功创建功能组!";
    public static final String OK_TO_UPDATE_SERVICE="成功修改服务!";
    public static final String OK_TO_DELETE_USER="成功删除用户!";
    public static final String OK_TO_LOCK_USER="成功冻结用户!";
    public static final String OK_TO_UNLOCK_USER="成功解冻用户!";
    public static final String OK_TO_CHANGE_PASSWORD="成功修改密码!";
    public static final String OK_TO_DELETE_SERVICE="成功删除服务!";

    public static final String FAILD_TO_ADD_USER = "添加用户失败";
    public static final String FAILD_TO_UPDATE_USER = "修改用户失败";
    public static final String FAILD_TO_DELETE_USER = "删除用户失败";
    public static final String FAILD_TO_LOCK_USER = "冻结用户失败";
    public static final String FAILD_TO_LOCK_SELF = "不能冻结当前账户";
    public static final String FAILD_TO_UNLOCK_USER = "解冻用户失败";
    public static final String FAILD_TO_CHANGE_PASSWORD = "修改密码失败";
    public static final String FAILD_TO_QUERY_SERVICE = "查询服务失败";
    public static final String FAILD_TO_QUERY_FEATUREGROUNP = "查询功能组失败";
    public static final String FAILD_TO_CREATE_SERVICE = "创建服务失败";
    public static final String FAILD_TO_CREATE_PERMGROUP = "创建授权组";
    public static final String FAILD_TO_LOCK_PERMGROUP = "禁用授权组失败";
    /*---end---*/

    public static final String FAILD_TO_VERIFY_STRATEGY = "策略校验失败";
    public static final String FAILD_TO_FIND_STRATEGY = "策略校验失败，策略尚未注册";
    public static final String OK_TO_LOCK_PERMGROUP = "成功禁用授权组";
    public static final String FAILD_TO_UNLOCK_PERMGROUP = "解锁授权组失败";
    public static final String OK_TO_UNLOCK_PERMGROUP = "成功解禁授权组";
    public static final String OK_TO_DELETE_PERMGROUP = "成功删除授权组";
    public static final String FAILD_TO_DELETE_PERMGROUP = "删除授权组失败";
}
