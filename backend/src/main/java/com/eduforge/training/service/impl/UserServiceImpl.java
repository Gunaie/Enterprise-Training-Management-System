package com.eduforge.training.service.impl;

import com.eduforge.training.mapper.SysUserMapper;
import com.eduforge.training.pojo.LoginInfo;
import com.eduforge.training.pojo.SysUser;
import com.eduforge.training.service.UserService;
import com.eduforge.training.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public List<SysUser> findAll() {
        return userMapper.findAll();
    }

    @Override
    public SysUser findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public LoginInfo login(String username, String password) {
        SysUser user = userMapper.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        String token = jwtUtils.generateToken(claims);
        log.info("登录成功，用户ID: {}, 用户名: {}", user.getId(), user.getUsername());
        return new LoginInfo(user.getId(), user.getUsername(), user.getName(), token);
    }
}
