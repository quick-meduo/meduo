/*
 * Copyright (c) 2020 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *     https://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.quick.meduo.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.quick.meduo.admin.model.PermissionGroupModel;
import org.quick.meduo.admin.service.PermissionGroupService;
import org.quick.meduo.admin.strategy.IPermissionGroupStrategy;
import org.quick.meduo.admin.vo.PermissionGroupBean;
import org.quick.meduo.core.annotations.ExecStrategy;
import org.quick.meduo.core.constant.ApiConstants;
import org.quick.meduo.core.http.AppStatus;
import org.quick.meduo.core.http.Page;
import org.quick.meduo.core.http.Result;
import org.quick.meduo.tools.core.date.DateField;
import org.quick.meduo.tools.core.date.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
  *@Description:
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2020/7/20
  *@Since: 1.0
  */
 @RestController
 @RequestMapping("/api/v1/meduo/admin/PermissionGroup")
public class PermissionGroupController {
    @Autowired
    PermissionGroupService permissionGroupService;

    @ExecStrategy(IPermissionGroupStrategy.class)
    @PostMapping(value="/create")
    public Result create(@RequestBody PermissionGroupBean model, HttpServletRequest request) {
        LambdaQueryWrapper<PermissionGroupModel> query = new LambdaQueryWrapper<>();
        query.eq(PermissionGroupModel::getName,model.getName());
        PermissionGroupModel service = permissionGroupService.getOne(query);
        if(service != null) {
            return Result.error(AppStatus.APC_NOT_ALLOWED, ApiConstants.FAILD_TO_CREATE_PERMGROUP);
        }
        PermissionGroupModel perm = new PermissionGroupModel();
        perm.setDescription(model.getDescription());
        perm.setFrozen(false);
        perm.setServiceId(model.getServiceId());
        perm.setType(model.getType());
        perm.setName(model.getName());

        if(model.getExpirationDate() != 0) {
            perm.setExpirationDate(DateTime.now().offsetNew(DateField.DAY_OF_YEAR,model.getExpirationDate()));
        }

        permissionGroupService.save(perm);
        return Result.ok(ApiConstants.OK_TO_CREATE_PERMGROUP);
    }

    @ExecStrategy(IPermissionGroupStrategy.class)
    @PutMapping(value="/lock")
    public Result lock(@RequestParam String id, HttpServletRequest request) {
        LambdaQueryWrapper<PermissionGroupModel> query = new LambdaQueryWrapper<>();
        query.eq(PermissionGroupModel::getId,id);
        PermissionGroupModel model = permissionGroupService.getOne(query);
        if(model == null) {
            return Result.error(AppStatus.APC_NOT_ALLOWED, ApiConstants.FAILD_TO_LOCK_PERMGROUP);
        }
        model.setFrozen(true);
        permissionGroupService.saveOrUpdate(model);
        return Result.ok(ApiConstants.OK_TO_LOCK_PERMGROUP);
    }

    @ExecStrategy(IPermissionGroupStrategy.class)
    @PutMapping(value="/unlock")
    public Result unlock(@RequestParam String id, HttpServletRequest request) {
        LambdaQueryWrapper<PermissionGroupModel> query = new LambdaQueryWrapper<>();
        query.eq(PermissionGroupModel::getId,id);
        PermissionGroupModel model = permissionGroupService.getOne(query);
        if(model == null) {
            return Result.error(AppStatus.APC_NOT_ALLOWED, ApiConstants.FAILD_TO_UNLOCK_PERMGROUP);
        }
        model.setFrozen(false);
        permissionGroupService.saveOrUpdate(model);
        return Result.ok(ApiConstants.OK_TO_UNLOCK_PERMGROUP);
    }

    @ExecStrategy(IPermissionGroupStrategy.class)
    @DeleteMapping(value="/delete")
    public Result delete(@RequestBody List<String> records, HttpServletRequest request) {
        Boolean result = permissionGroupService.removeByIds(records);
        if(result){
            return Result.ok(ApiConstants.OK_TO_DELETE_PERMGROUP);
        }
        return Result.error(ApiConstants.FAILD_TO_DELETE_PERMGROUP);
    }

    @ExecStrategy(IPermissionGroupStrategy.class)
    @PutMapping(value="/findPermissionGroupByServiceId")
    @Transactional
    public Result findPermissionGroupByServiceId(@RequestBody Page<String> query) {
        String serviceId = query.getParams();
        LambdaQueryWrapper<PermissionGroupModel> queryWrapper = new LambdaQueryWrapper<PermissionGroupModel>().eq(PermissionGroupModel::getServiceId,serviceId);

        return Result.ok(permissionGroupService.page(query,queryWrapper));
    }
}
