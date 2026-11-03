package com.eduforge.training.controller;

import com.eduforge.training.pojo.Permission;
import com.eduforge.training.pojo.Result;
import com.eduforge.training.pojo.Role;
import com.eduforge.training.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Tag(name = "角色管理", description = "角色和权限管理相关接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @Operation(summary = "获取角色列表", description = "获取所有角色")
    public Result<List<Role>> list() {
        return Result.success(roleService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取角色详情", description = "根据ID获取角色详情")
    public Result<Role> getInfo(@PathVariable Integer id) {
        Role role = roleService.findById(id);
        if (role == null) {
            return Result.notFound("角色不存在");
        }
        return Result.success(role);
    }

    @GetMapping("/permissions/{userId}")
    @Operation(summary = "获取用户权限", description = "根据用户ID获取其拥有的权限")
    public Result<List<Permission>> getPermissions(@PathVariable Integer userId) {
        return Result.success(roleService.findPermissionsByUserId(userId));
    }

    @PostMapping
    @Operation(summary = "创建角色", description = "创建新角色")
    public Result<Void> save(@RequestBody Role role) {
        roleService.save(role);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "更新角色", description = "更新角色信息")
    public Result<Void> update(@RequestBody Role role) {
        roleService.update(role);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色", description = "删除角色")
    public Result<Void> delete(@PathVariable Integer id) {
        roleService.delete(id);
        return Result.success();
    }
}
