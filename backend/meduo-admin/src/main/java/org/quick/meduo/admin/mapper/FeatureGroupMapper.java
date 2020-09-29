package org.quick.meduo.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.session.RowBounds;
import org.quick.meduo.admin.model.FeatureGroupModel;
import org.quick.meduo.admin.vo.FeatureGroupBean;
import org.quick.meduo.core.http.Page;
import org.quick.meduo.core.mapper.MeduoBaseMapper;

import java.util.List;

/**
 *@Description:
 *@TODO:
 *@Changelog:
 *@Authors: Brian.G @2020/07/07
 *@Since: 1.0  
 */
public interface FeatureGroupMapper  extends MeduoBaseMapper<FeatureGroupModel> {
    List<FeatureGroupModel> selectFeatureGroup(String serviceid);
    Page<FeatureGroupModel> selectFeatureGroupWrapper(Page<?> page, FeatureGroupBean query);
}