package com.eduforge.training.service.impl;

import com.eduforge.training.mapper.PermissionMapper;
import com.eduforge.training.mapper.RoleMapper;
import com.eduforge.training.mapper.UserRoleMapper;
import com.eduforge.training.pojo.Permission;
import com.eduforge.training.pojo.Role;
import com.eduforge.training.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public Role findById(Integer id) {
        return roleMapper.findById(id);
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleMapper.insert(role);
    }

    @Override
    @Transactional
    public void update(Role role) {
        roleMapper.update(role);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        userRoleMapper.deleteByRoleId(id);
        roleMapper.delete(id);
    }

    @Override
    public List<Role> findByUserId(Integer userId) {
        return roleMapper.findByUserId(userId);
    }

    @Override
    public List<Permission> findPermissionsByUserId(Integer userId) {
        return permissionMapper.findByUserId(userId);
    }

    @Override
    public boolean hasPermission(Integer userId, String permissionCode) {
        List<Permission> permissions = permissionMapper.findByUserId(userId);
        Set<String> permissionCodes = permissions.stream()
                .map(Permission::getCode)
                .collect(Collectors.toSet());
        return permissionCodes.contains(permissionCode);
    }
}
