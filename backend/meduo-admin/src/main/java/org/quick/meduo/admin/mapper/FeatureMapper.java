package org.quick.meduo.admin.mapper;

import org.quick.meduo.admin.model.FeatureModel;
import org.quick.meduo.core.mapper.MeduoBaseMapper;

import java.util.List;

/**
 *@Description:
 *@TODO:
 *@Changelog:
 *@Authors: Brian.G @2020/07/07
 *@Since: 1.0  
 */
public interface FeatureMapper extends MeduoBaseMapper<FeatureModel> {
    List<FeatureModel> selectFeatures(String gid);
}