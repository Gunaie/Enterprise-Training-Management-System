package com.eduforge.training.config;

import com.eduforge.training.mapper.SysUserMapper;
import com.eduforge.training.pojo.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitConfig {

    private static final Logger log = LoggerFactory.getLogger(DataInitConfig.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        try {
            List<SysUser> users = userMapper.findAll();
            boolean needUpdate = false;
            for (SysUser user : users) {
                String password = user.getPassword();
                if (!password.startsWith("$2a$") && !password.startsWith("$2b$") && !password.startsWith("$2y$")) {
                    needUpdate = true;
                    break;
                }
            }
            if (needUpdate) {
                log.info("检测到明文密码，开始加密...");
                for (SysUser user : users) {
                    String password = user.getPassword();
                    if (!password.startsWith("$2a$") && !password.startsWith("$2b$") && !password.startsWith("$2y$")) {
                        user.setPassword(passwordEncoder.encode(password));
                        user.setUpdateTime(LocalDateTime.now());
                        userMapper.updateById(user);
                        log.info("已加密用户密码: {}", user.getUsername());
                    }
                }
                log.info("密码加密完成");
            }
        } catch (Exception e) {
            log.error("密码加密失败", e);
        }
    }
}
