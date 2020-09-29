package org.quick.meduo.core.http;

 /**
  *@Description:
  * Application status code definition
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2019/11/13
  *@Since: 1.0
  */
public interface AppStatus {
    public static final  int APC_OK = 200;
    public static final  int APC_SYSTEM_INTIAL = 201;
    public static final  int APC_FE_ERROR= 400;
    public static final int APC_ERROR = 500;
    public static final int APC_ACCOUNT_NOT_EXIST = 501;
     public static final int APC_ACCOUNT_DUP = 502;
    public static final int APC_NOT_ALLOWED = 502;
}
