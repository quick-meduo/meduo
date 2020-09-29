package org.quick.meduo.admin.service.impl;

import org.quick.meduo.admin.mapper.UserMapper;
import org.quick.meduo.admin.mapper.UserRoleMapper;
import org.quick.meduo.admin.model.UserModel;
import org.quick.meduo.admin.model.UserRole;
import org.quick.meduo.admin.service.UserService;
import org.quick.meduo.core.service.impl.MeduoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends MeduoServiceImpl<UserMapper, UserModel> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> findUserRoles(String userId) {
        return userRoleMapper.findUserRoles(userId);
    }

    @Override
    public UserModel QueryByPrimaryKey(String key){
        return userMapper.QueryByPrimaryKey(key);
    }
}
