package com.tuyrk.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.quickstart.dao.UserMapper;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SelectSimpleTest extends ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectById() {
        User user = userMapper.selectById(1094590409767661570L);
        System.out.println(user);
        Assertions.assertEquals(1094590409767661570L, user.getId());
        Assertions.assertEquals("张雨琪", user.getName());
    }

    @Test
    public void selectBatchIds() {
        List<Long> ids = Arrays.asList(1094590409767661570L, 1088248166370832385L, 1094592041087729666L);
        List<User> userList = userMapper.selectBatchIds(ids);
        userList.forEach(System.out::println);
        List<Long> collect = userList.stream().map(User::getId).collect(Collectors.toList());
        Assertions.assertEquals(ids.size(), collect.size());
    }

    @Test
    public void selectByMap() {
        /**
         * key为列名，而不是实体的属性名
         * map.put("name", "王天风");
         * map.put("age", "30");
         * where name='王天风' and age=30
         */
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "王天风");
        columnMap.put("age", 25);
        List<User> userList = userMapper.selectByMap(columnMap);
        userList.forEach(System.out::println);
    }

    /**
     * 测试condition的作用
     * 是否将其作为查询条件加入到执行的SQL语句
     */
    @Test
    public void testCondition() {
        String name = "";
        String email = "com";
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        /*if (StringUtils.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        if (StringUtils.isNotBlank(email)) {
            queryWrapper.like("email", email);
        }*/
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                .like(StringUtils.isNotBlank(email), "email", email);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }
}
