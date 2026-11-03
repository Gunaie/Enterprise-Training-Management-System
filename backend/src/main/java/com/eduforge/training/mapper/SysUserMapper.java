package com.eduforge.training.mapper;

import com.eduforge.training.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {

    List<SysUser> findAll();

    SysUser findById(Integer id);

    SysUser findByUsername(String username);

    void updateById(SysUser user);
}
