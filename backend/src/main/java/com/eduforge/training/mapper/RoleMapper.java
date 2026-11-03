package com.eduforge.training.mapper;

import com.eduforge.training.pojo.Permission;
import com.eduforge.training.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> findAll();
    Role findById(Integer id);
    Role findByCode(String code);
    void insert(Role role);
    void update(Role role);
    void delete(Integer id);
    List<Role> findByUserId(Integer userId);
    List<Permission> findPermissionsByRoleId(Integer roleId);
}
