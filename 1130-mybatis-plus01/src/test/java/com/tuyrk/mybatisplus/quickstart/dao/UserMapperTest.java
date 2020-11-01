package com.tuyrk.mybatisplus.quickstart.dao;

import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserMapperTest extends ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
        // 参数为null表示查询全部数据
        List<User> userList = userMapper.selectList(null);
        Assertions.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }
}
