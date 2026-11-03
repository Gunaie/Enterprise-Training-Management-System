package com.eduforge.training.service;

import com.eduforge.training.pojo.Permission;
import com.eduforge.training.pojo.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(Integer id);

    void save(Role role);

    void update(Role role);

    void delete(Integer id);

    List<Role> findByUserId(Integer userId);

    List<Permission> findPermissionsByUserId(Integer userId);

    boolean hasPermission(Integer userId, String permissionCode);
}
