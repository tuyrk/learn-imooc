package com.tuyrk.mybatisplus.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OptMapperTest extends ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void updateById() {
        int version = 1;
        User user = new User();
        user.setId(1324399185079832578L);
        user.setVersion(version);
        user.setEmail("lmm2@baomidou.com");
        int rows = userMapper.updateById(user);
        System.out.println("影响行数：" + rows);
    }

    @Test
    public void update() {
        int version = 3;
        User user = new User();
        user.setVersion(version);
        user.setEmail("lmm3@baomidou.com");
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery()
                .eq(User::getId, 1324399185079832578L);
        int rows = userMapper.update(user, queryWrapper);
        System.out.println("影响行数：" + rows);

        int version2 = 4;
        user.setVersion(version2);
        user.setEmail("lmm4@baomidou.com");
        int rows2 = userMapper.update(user, queryWrapper);
        System.out.println("影响行数：" + rows2);
    }
}
