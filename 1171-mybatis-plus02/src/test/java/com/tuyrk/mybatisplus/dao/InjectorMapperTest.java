package com.tuyrk.mybatisplus.dao;

import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class InjectorMapperTest extends ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 删除所有数据
     */
    @Test
    public void deleteAll() {
        int rows = userMapper.deleteAll();
        System.out.println("影响行数：" + rows);
    }

    /**
     * 批量新增数据,自选字段 insert(排除逻辑删除字段)
     */
    @Test
    public void insertBatchSomeColumn() {
        User user1 = new User();
        user1.setName("李兴华");
        user1.setAge(34);
        user1.setManagerId(1088248166370832385L);
        User user2 = new User();
        user2.setName("杨红");
        user2.setAge(29);
        user2.setManagerId(1088248166370832385L);

        int rows = userMapper.insertBatchSomeColumn(Arrays.asList(user1, user2));
        System.out.println("影响行数：" + rows);
    }

    /**
     * 根据 id 逻辑删除数据,并带字段填充功能
     */
    @Test
    public void deleteByIdWithFill() {
        User user = new User();
        user.setId(1327906230311628802L);
        user.setAge(35);

        int rows = userMapper.deleteByIdWithFill(user);
        System.out.println("影响行数：" + rows);
    }

    /**
     * 根据 ID 更新固定的那几个字段(但是不包含逻辑删除)
     */
    @Test
    public void alwaysUpdateSomeColumnById() {
        User user = new User();
        user.setId(1327906230311628802L);
        user.setAge(40);
        user.setName("李兴国");

        int rows = userMapper.alwaysUpdateSomeColumnById(user);
        System.out.println("影响行数：" + rows);
    }
}
