package org.quick.meduo.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.quick.meduo.admin.model.UserRole;
import org.quick.meduo.core.mapper.MeduoBaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface UserRoleMapper extends MeduoBaseMapper<UserRole> {
    List<UserRole> findUserRoles(@Param(value = "userId") String userId);
}