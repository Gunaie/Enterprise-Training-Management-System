package com.eduforge.training.mapper;

import com.eduforge.training.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    void insert(UserRole userRole);

    void deleteByUserId(Integer userId);

    void deleteByRoleId(Integer roleId);

    List<UserRole> findByUserId(Integer userId);
}
