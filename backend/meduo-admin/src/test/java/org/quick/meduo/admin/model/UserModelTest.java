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

package org.quick.meduo.admin.model;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quick.meduo.admin.mapper.UserMapper;
import org.quick.meduo.common.constants.UserState;
import org.quick.meduo.common.constants.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Test User Operations
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserModelTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void TestAddUser(){
        UserModel u = new UserModel();
        u.setUid("admin001");
        u.setType(UserType.ADMIN);
        u.setState(UserState.NORMAL);
        u.insertOrUpdate();
    }

    @Test
    public void TestUpdate(){
        UserModel u = new UserModel();
        u.setId("0c676a2dc6f4eefa75bff1d2560dbbb4");
        u.setFrozen(false);
        u.setName("gaosg");
        u.setAvatar("^^^^^");

        userMapper.updateById(u);

        u.setId("86f0d3812e1c68d4a61dfb23c1667271");
        u.setName("jiangy");
        u.setAvatar("@@@@@");
        userMapper.updateById(u);
    }

    @Test
    public void TestSelectAll(){
        UserModel u = new UserModel();
        List<UserModel> list = u.selectAll();
        return;
    }

    @Test
    public void TestSelectPage(){
        UserModel u = new UserModel();
        IPage<UserModel> list = u.selectPage(new Page<UserModel>(1,10),null);
        return;
    }

    @Test
    public void TestSelectQuery(){
        LambdaQueryWrapper<UserModel> q = new LambdaQueryWrapper<UserModel>();
        q.eq(UserModel::getUid,"admin");

        UserModel u = new UserModel();

        IPage<UserModel> list = u.selectPage(new Page<UserModel>(1,10),q);

        return;
    }

    @Test
    public void testUdpateSelective(){
//        LambdaUpdateWrapper<User> up = new LambdaUpdateWrapper<User>();
//        up.
//        User u = new User();
//        u.setId("86f0d3812e1c68d4a61dfb23c1667271");
//
//        userMapper.update();
    }
}