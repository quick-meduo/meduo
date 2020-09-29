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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.logging.log4j.util.Strings;
import org.quick.meduo.admin.mapper.FeatureGroupMapper;
import org.quick.meduo.admin.mapper.FeatureMapper;
import org.quick.meduo.admin.model.FeatureGroupModel;
import org.quick.meduo.admin.model.FeatureModel;
import org.quick.meduo.admin.model.ServiceModel;
import org.quick.meduo.admin.model.UserModel;
import org.quick.meduo.admin.service.FeatureGroupService;
import org.quick.meduo.admin.service.FeatureService;
import org.quick.meduo.admin.strategy.IServiceExecStrategy;
import org.quick.meduo.admin.strategy.impl.DefaultServiceStrategy;
import org.quick.meduo.admin.vo.FeatureGroupBean;
import org.quick.meduo.core.annotations.ExecStrategy;
import org.quick.meduo.core.constant.ApiConstants;
import org.quick.meduo.core.constant.ErrorConstants;
import org.quick.meduo.core.http.AppStatus;
import org.quick.meduo.core.http.Page;
import org.quick.meduo.core.http.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

/**
 *@Description:
 *@TODO:
 *@Changelog:
 *@Authors: Brian.G @2020/7/7
 *@Since: 1.0
 */
@RestController
@RequestMapping("/api/v1/meduo/admin/service")
public class ServiceController {
    @Autowired
    private FeatureService featureService;

    @Autowired
    private FeatureGroupService featureGroupService;

    @Autowired
    private FeatureMapper featureMapper;

    @ExecStrategy(IServiceExecStrategy.class)
    @GetMapping(value="/list")
    public Result list(HttpServletRequest request) {
        List<ServiceModel> models = featureService.list();
        return Result.build(models != null && models.size() != 0,models,ApiConstants.FAILD_TO_QUERY_SERVICE);
    }

//    @ExecStrategy(IServiceExecStrategy.class)
    @PutMapping(value="/listPage")
    public Result findPage(@RequestBody Page req) {
        return Result.ok(featureService.page(req));
    }

    @ExecStrategy(IServiceExecStrategy.class)
    @PostMapping(value="/create")
    public Result create(@RequestBody ServiceModel model, HttpServletRequest request) {
        LambdaQueryWrapper<ServiceModel> query = new LambdaQueryWrapper<>();
        query.eq(ServiceModel::getName,model.getName());
        ServiceModel service = featureService.getOne(query);
        if(service != null) {
            return Result.error(AppStatus.APC_NOT_ALLOWED, ApiConstants.FAILD_TO_CREATE_SERVICE);
        }
        featureService.save(model);
        return Result.ok(ApiConstants.OK_TO_CREATE_SERVICE);
    }

    @ExecStrategy(IServiceExecStrategy.class)
    @PutMapping(value="/update")
    public Result update(@RequestBody ServiceModel model,HttpServletRequest request) {
        featureService.saveOrUpdate(model);
        return Result.ok(ApiConstants.OK_TO_UPDATE_SERVICE);
    }

    @ExecStrategy(IServiceExecStrategy.class)
    @DeleteMapping(value="/delete")
    public Result delete(@RequestParam String id,HttpServletRequest request) {
        ServiceModel serviceModel = featureService.getById(id);
        if (null == serviceModel) {
            return Result.error(AppStatus.APC_FE_ERROR, ErrorConstants.ERROR_FE_ERROR);
        }

        List<FeatureGroupModel> groups = serviceModel.getFeatureGroupModels();

        if ( null == groups ){
            featureService.removeById(id);
            return  Result.ok(ApiConstants.OK_TO_DELETE_SERVICE);
        }

        // remove feature list
        for (FeatureGroupModel fg: groups){
            List<String> idList = fg.getFeatures().stream().map(FeatureModel::getId).collect(Collectors.toList()) ;
            if( null != idList && idList.size() >= 0 ){
                featureMapper.deleteBatchIds(idList);
            }
        }

        //remove feature groups
        List<String> idList = groups.stream().map(FeatureGroupModel::getId).collect(Collectors.toList()) ;
        if( null != idList && idList.size() > 0 ) {
            featureGroupService.removeByIds(idList);
        }

        //remove service
        featureService.removeById(id);

        return  Result.ok(ApiConstants.OK_TO_DELETE_SERVICE);
    }

    @GetMapping(value="/validateSid")
    public Result validateSid(@RequestParam String sid) {
        LambdaQueryWrapper<ServiceModel> query = new LambdaQueryWrapper<>();
        query.eq(ServiceModel::getSid,sid);

        ServiceModel  serviceOne = featureService.getOne(query);

        boolean found = false;
        if( serviceOne != null) {
            found = true;
        }
        return Result.ok(found);
    }

    @PutMapping(value="/findServiceByFeatureKey")
    public Result findServiceByFeatureKey(@RequestBody Page<String> query) {
        String key = query.getParams();
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<ServiceModel>().like(ServiceModel::getSid,key).
                or().like(ServiceModel::getName,key).
                or().like(ServiceModel::getDescription,key);
        IPage<ServiceModel> results = featureService.page(query,queryWrapper);
        return Result.build(results!=null,results,ApiConstants.FAILD_TO_QUERY_SERVICE);
    }

    @ExecStrategy(IServiceExecStrategy.class)
    @PostMapping(value = "/addFeatureGroup")
    public Result addFeatureGroup(@RequestBody @Validated FeatureGroupModel model) {
        featureGroupService.save(model);
        return Result.ok(ApiConstants.OK_TO_CREATE_FEATURE_GROUP);
    }

    @ExecStrategy(IServiceExecStrategy.class)
    @PutMapping(value = "/findFeatureGroupsByServiceId")
    public Result findFeatureGroupsByServiceId(@RequestBody Page<String> query) {
        String serviceId = query.getParams();
        LambdaQueryWrapper<FeatureGroupModel> queryWrapper = new LambdaQueryWrapper<FeatureGroupModel>().eq(FeatureGroupModel::getServiceId,serviceId);

        return Result.ok(featureGroupService.page(query,queryWrapper));
    }

    @ExecStrategy(IServiceExecStrategy.class)
    @PutMapping(value = "/findFeatureGroups")
    public Result findFeatureGroups(@RequestBody @Valid Page<FeatureGroupBean> query) {
        FeatureGroupBean params = query.getParams();
        return Result.ok(featureGroupService.listFeatureGroup(query,params));
    }

    @ExecStrategy(IServiceExecStrategy.class)
    @PostMapping(value = "/addFeature")
    public Result createFeature(@RequestBody @Valid FeatureModel model) {
        return Result.ok(featureMapper.insert(model));
    }

    @ExecStrategy(IServiceExecStrategy.class)
    @PutMapping(value = "/updateFeature")
    public Result updateFeature(@RequestBody @Valid FeatureModel model) {
        return Result.ok(featureMapper.updateById(model));
    }

    @ExecStrategy(IServiceExecStrategy.class)
    @PutMapping(value = "/findFeaturesByGroupId")
    public Result findFeaturesByGroupId(@RequestBody Page<String> query) {
        String group = query.getParams();
        LambdaQueryWrapper<FeatureModel> queryWrapper = new LambdaQueryWrapper<FeatureModel>().eq(FeatureModel::getGid,group);
        return Result.ok(featureMapper.selectPage(query,queryWrapper));
    }
}
