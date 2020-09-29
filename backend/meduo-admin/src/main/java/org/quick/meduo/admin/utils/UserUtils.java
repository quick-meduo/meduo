package org.quick.meduo.admin.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.quick.meduo.admin.model.UserModel;
import org.quick.meduo.admin.service.UserService;
import org.quick.meduo.common.constants.UserState;
import org.quick.meduo.core.config.MeduoContextHolder;

/**
 * User Utility
 */
public class UserUtils {

    /**
     * Only allow user which state is normal
     * @param u
     * @return
     */
    public static boolean isAllowed(UserModel u){
        boolean result = false;
        if(u != null){
            if(u.getState() == UserState.NORMAL && !u.getFrozen()){
                result = true;
            }
        }
        return result;
    }

    public static boolean isFrozed(UserModel u){
        boolean result = false;
        if(u != null){
            result = u.getFrozen();
        }
        return result;
    }

    public static boolean isLocked(UserModel u){
        boolean result = false;
        if(u != null){
            if(u.getState() == UserState.LOCKED){
                result = true;
            }
        }
        return result;
    }

    public static boolean isAbnormal(UserModel u){
        boolean result = false;
        if(u != null){
            if(u.getState() != UserState.NORMAL){
                result = true;
            }
        }
        return result;
    }

    public static String currentUserId(){
        return SecurityUtils.getUsername();
    }

    public static UserModel currentUser(){
        UserService service = MeduoContextHolder.getBean(UserService.class);
        UserModel currentUser = service.getOne(new LambdaQueryWrapper<UserModel>().eq(UserModel::getUid,UserUtils.currentUserId()));

        return currentUser;
    }
}
