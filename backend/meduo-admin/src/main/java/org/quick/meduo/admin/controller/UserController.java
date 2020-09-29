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

package org.quick.meduo.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.quick.meduo.admin.audit.AuditHelper;
import org.quick.meduo.admin.model.UserModel;
import org.quick.meduo.admin.service.UserService;
import org.quick.meduo.admin.strategy.IUserExecStrategy;
import org.quick.meduo.admin.strategy.impl.DefaultUserStrategy;
import org.quick.meduo.admin.utils.PasswordUtils;
import org.quick.meduo.admin.utils.UserUtils;
import org.quick.meduo.admin.vo.ResetPasswordBean;
import org.quick.meduo.admin.vo.UpdatePasswordBean;
import org.quick.meduo.common.utils.StringUtils;
import org.quick.meduo.contacts.AuditTypeDefinition;
import org.quick.meduo.core.annotations.ExecStrategy;
import org.quick.meduo.core.constant.ApiConstants;
import org.quick.meduo.core.constant.ErrorConstants;
import org.quick.meduo.core.http.AppStatus;
import org.quick.meduo.core.http.Page;
import org.quick.meduo.core.http.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
  *@Description:
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2019/11/12
  *@Since: 1.0
  */
 @RestController
 @RequestMapping("/api/v1/meduo/admin/user")
public class UserController {
    @Autowired
    private UserService userService;

//    @PreAuthorize("hasAuthority('sys:user:add') AND hasAuthority('sys:user:edit')")
    @PostMapping(value="/save")
    public Result save(@RequestBody UserModel record, HttpServletRequest request) {
        if(record == null){
            return Result.error(AppStatus.APC_FE_ERROR,ErrorConstants.ERROR_FE_ERROR);
        }

        UserModel currentUser = currentUser();
        LambdaQueryWrapper<UserModel> query = new LambdaQueryWrapper<>();
        query.eq(UserModel::getId,record.getId());
        UserModel user = userService.getOne(query);
        if(user == null){
            //add user logic path
            query = new LambdaQueryWrapper<>();
            query.eq(UserModel::getUid,record.getUid());
            if(userService.getOne(query)!=null){
                return Result.error(AppStatus.APC_ACCOUNT_DUP,ErrorConstants.ERROR_ACCOUNT_DUP);
            }
            String salt = PasswordUtils.getSalt();
            String password = PasswordUtils.encode(record.getEncryptedPassword(), salt);
            record.setSalt(salt);
            record.setPassword(password);
            Boolean result = userService.save(record);
            if(result){
                //Record audit log
                AuditHelper.save(AuditTypeDefinition.info,currentUser.getUid(),currentUser.getName(),request.getRemoteAddr(),"添加用户",ApiConstants.OK_TO_ADD_USER,"允许");
                return Result.ok(ApiConstants.OK_TO_ADD_USER,AppStatus.APC_OK);
            }else{
                AuditHelper.save(AuditTypeDefinition.error,currentUser.getUid(),currentUser.getName(),request.getRemoteAddr(),"添加用户",ApiConstants.FAILD_TO_ADD_USER,"允许");
                return Result.error(AppStatus.APC_ERROR,ApiConstants.FAILD_TO_ADD_USER);
            }
        }

        Boolean result = false;
        if(StringUtils.isNotBlank(record.getPassword())){
            // 修改用户, 且修改了密码
            String salt = PasswordUtils.getSalt();
            String password = PasswordUtils.encode(record.getEncryptedPassword(), salt);
            record.setSalt(salt);
            record.setPassword(password);
            result = userService.saveOrUpdate(record);
        } else {
            result = userService.saveOrUpdate(record);
        }

        if(result){
            AuditHelper.save(AuditTypeDefinition.info,currentUser.getUid(),currentUser.getName(),request.getRemoteAddr(),"修改用户",ApiConstants.OK_TO_UPDATE_USER,"允许");
        }else {
            AuditHelper.save(AuditTypeDefinition.error,currentUser.getUid(),currentUser.getName(),request.getRemoteAddr(),"修改用户",ApiConstants.FAILD_TO_UPDATE_USER,"允许");
        }
        return Result.ok(ApiConstants.OK_TO_UPDATE_USER);
    }

//    @PreAuthorize("hasAuthority('sys:user:delete')")
    @ExecStrategy(IUserExecStrategy.class)
    @DeleteMapping(value="/delete")
    public Result delete(@RequestBody List<UserModel> records, HttpServletRequest request) {
        List<String> idList = records.stream().map(UserModel::getId).collect(Collectors.toList()) ;
        Boolean result = userService.removeByIds(idList);
        if(result){
            return Result.ok(ApiConstants.OK_TO_DELETE_USER);
        }
        return Result.error(ApiConstants.FAILD_TO_DELETE_USER);
    }

    @ExecStrategy(IUserExecStrategy.class)
    @PutMapping(value="/lock")
    public Result lock(@RequestParam String id) {
        UserModel me  = currentUser();
        if(currentUser().getId().equals(id)){
            return Result.error(ApiConstants.FAILD_TO_LOCK_SELF);
        }
        UserModel user = userService.getOne(new LambdaQueryWrapper<UserModel>().eq(UserModel::getId,id));
        boolean found = false;
        if( user == null) {
            return Result.error(ApiConstants.FAILD_TO_LOCK_USER);
        }
        user.setFrozen(true);
        userService.saveOrUpdate(user);
        return Result.ok(ApiConstants.OK_TO_LOCK_USER);
    }

    @PutMapping(value="/unlock")
    public Result unlock(@RequestParam String id) {
        UserModel user = userService.getOne(new LambdaQueryWrapper<UserModel>().eq(UserModel::getId,id));
        if( user == null) {
            return Result.error(ApiConstants.FAILD_TO_UNLOCK_USER);
        }
        user.setFrozen(false);
        userService.saveOrUpdate(user);
        return Result.ok(ApiConstants.OK_TO_UNLOCK_USER);
    }

//    @PreAuthorize("hasAuthority('sys:user:view')")
    @PutMapping(value="/findByName")
    public Result findByUserName(@RequestBody Page<UserModel> query) {
        String uname = query.getParams().getName();
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<UserModel>().like(UserModel::getUid,uname).or().like(UserModel::getName,uname);
        IPage<UserModel> results = userService.page(query,queryWrapper);
        return Result.build(results!=null,results,ErrorConstants.ERROR_ACCOUNT_NOT_EXIST);
    }

//    @PreAuthorize("hasAuthority('sys:user:view')")
//    @GetMapping(value="/findPermissions")
//    public HttpResult findPermissions(@RequestParam String name) {
//        return HttpResult.ok(sysUserService.findPermissions(name));
//    }
//
    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value="/findUserRoles")
    public Result findUserRoles(@RequestParam String userId) {
        return Result.ok(userService.findUserRoles(userId));
    }

//    @PreAuthorize("hasAuthority('sys:user:view')")
    @PostMapping(value="/findPage")
    public Result findPage(@RequestBody Page pageRequest) {
        return Result.ok(userService.page(pageRequest));
    }
//
//    @PreAuthorize("hasAuthority('sys:user:view')")
//    @PostMapping(value="/exportUserExcelFile")
//    public HttpResult exportUserExcelFile(@RequestBody Page<User> userPage) {
//        return HttpResult.ok(sysUserService.createUserExcelFile(pageRequest));
//    }

    //@PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value="/validateUid")
    public Result validateUid(@RequestParam String userId) {
        UserModel currentUser = userService.QueryByPrimaryKey(userId);

        boolean found = false;
        if( currentUser != null) {
            found = true;
        }
        return Result.ok(found);
    }

//    @PreAuthorize("hasAuthority('sys:user:passwd:update')")
    @PutMapping(value="/updatePassword")
    public Result updatePassword(@RequestBody UpdatePasswordBean pw) {
        UserModel user = userService.getOne(new LambdaQueryWrapper<UserModel>().eq(UserModel::getUid,UserUtils.currentUser()));
        if(user == null) {
            return Result.error(ApiConstants.FAILD_TO_CHANGE_PASSWORD);
        }

        if(!PasswordUtils.matches(user.getSalt(), pw.getPassword(), user.getPassword())) {
            return Result.error(ErrorConstants.ERROR_OLD_PASSWORD_MISMATCH);
        }
        String salt = PasswordUtils.getSalt();
        user.setPassword(PasswordUtils.encode(pw.getNewPassword(), salt));
        user.setSalt(salt);
        boolean result = userService.save(user);

        return Result.build(result,ApiConstants.OK_TO_CHANGE_PASSWORD,ApiConstants.FAILD_TO_CHANGE_PASSWORD);
    }

//    @PreAuthorize("hasAuthority('sys:user:passwd:reset')")
    @PutMapping(value="/resetPassword")
    public Result resetPassword(@RequestBody ResetPasswordBean pw) {
        return Result.error("SORRY，暂未实现");
    }

    private UserModel currentUser(){
        UserModel currentUser = userService.getOne(new LambdaQueryWrapper<UserModel>().eq(UserModel::getUid,UserUtils.currentUserId()));
        return currentUser;
    }
}
