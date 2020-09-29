package org.quick.meduo.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quick.meduo.admin.model.UserModel;
import org.quick.meduo.admin.model.UserRole;
import org.quick.meduo.admin.utils.PasswordUtils;
import org.quick.meduo.common.constants.UserState;
import org.quick.meduo.common.constants.UserType;
import org.quick.meduo.core.constant.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserModelService {
    @Autowired
    UserService userService;

    @Test
    public void TestQuery(){
        //Query User by using UID,here it is username
        LambdaQueryWrapper<UserModel> query = new LambdaQueryWrapper<>();
        query.eq(UserModel::getUid,"admin");
        UserModel user = userService.getOne(query);
        return;
    }

    @Test
    public void TestSaveUser(){
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
        return;
    }

    @Test
    public void TestJava(){
        List<UserModel> records = new ArrayList<>();
        UserModel u = new UserModel();
        u.setId("1");
        records.add(u);
        u = new UserModel();
        u.setId("2");
        records.add(u);

        List<String> idList = records.stream().map(UserModel::getId).collect(Collectors.toList()) ;

        return;
    }

    @Test
    public void TestXmlMapper(){
        List<UserRole> list = userService.findUserRoles("5ecfd974fd050c5ff97dc6c657ed8303");
        return;
    }
}
