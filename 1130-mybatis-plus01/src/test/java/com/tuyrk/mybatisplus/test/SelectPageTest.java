package com.tuyrk.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.quickstart.dao.UserMapper;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class SelectPageTest extends ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    /**
     * MybatisPlus分页
     */
    @Test
    public void selectPage() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.ge(User::getAge, 26);

        Page<User> page = new Page<>(1, 2);

        Page<User> userIPage = userMapper.selectPage(page, lambdaQuery);
        System.out.println("总页数：" + userIPage.getPages());
        System.out.println("总记录数：" + userIPage.getTotal());
        List<User> userList = userIPage.getRecords();
        userList.forEach(System.out::println);
    }

    /**
     * MybatisPlus分页
     */
    @Test
    public void selectMapsPage() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.ge(User::getAge, 26);

        Page<Map<String, Object>> page = new Page<>(1, 2, false);

        Page<Map<String, Object>> mapsPage = userMapper.selectMapsPage(page, lambdaQuery);
        System.out.println("总页数：" + mapsPage.getPages());
        System.out.println("总记录数：" + mapsPage.getTotal());
        List<Map<String, Object>> userList = mapsPage.getRecords();
        userList.forEach(System.out::println);
    }

    /**
     * MybatisPlus自定义分页
     */
    @Test
    public void selectUserPage() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.ge(User::getAge, 26);

        Page<User> page = new Page<>(1, 2);

        IPage<User> userIPage = userMapper.selectUserPage(page, lambdaQuery);
        System.out.println("总页数：" + userIPage.getPages());
        System.out.println("总记录数：" + userIPage.getTotal());
        List<User> userList = userIPage.getRecords();
        userList.forEach(System.out::println);
    }
}
