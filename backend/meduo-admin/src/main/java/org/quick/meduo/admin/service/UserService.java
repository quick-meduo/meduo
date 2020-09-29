package org.quick.meduo.admin.service;

import org.quick.meduo.admin.model.UserModel;
import org.quick.meduo.admin.model.UserRole;
import org.quick.meduo.core.service.IMeduoService;

import java.util.List;


public interface UserService extends IMeduoService<UserModel> {
    /**
     * Find Roles by give user id
     * @param userId
     * @return
     */
    List<UserRole> findUserRoles(String userId);
    UserModel QueryByPrimaryKey(String key);
}
