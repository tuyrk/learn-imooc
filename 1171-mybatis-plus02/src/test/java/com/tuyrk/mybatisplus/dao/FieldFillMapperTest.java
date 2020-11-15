package com.tuyrk.mybatisplus.dao;

import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FieldFillMapperTest extends ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setName("李国民");
        user.setAge(27);
        user.setEmail("lgm@baomidou.com");
        int rows = userMapper.insert(user);
        System.out.println("影响行数：" + rows);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setAge(28);
        user.setId(1088248166370832385L);
        // user.setUpdateTime(LocalDateTime.now());
        int rows = userMapper.updateById(user);
        System.out.println("影响行数：" + rows);
    }
}
