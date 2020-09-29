
package org.quick.meduo.admin.utils;

import org.quick.meduo.core.constant.ApiConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * Kaptcha Utility
 */
public class KaptchaUtils {
    public static boolean isRequired( HttpServletRequest request){
        boolean result = false;
        Object kaptcha = request.getSession().getAttribute(ApiConstants.KAPTCHA_SESSION_REQUIRED_KEY);
        if(kaptcha != null && ApiConstants.KAPTCHA_SESSION_REQUIRED_KEY.equalsIgnoreCase(kaptcha.toString())){
            result = true;
        }
        return result;
    }
}
