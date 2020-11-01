package com.tuyrk.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.quickstart.dao.UserMapper;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;

public class DeleteTest extends ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据ID删除
     */
    @Test
    public void deleteById() {
        int rows = userMapper.deleteById(1322819611623723009L);
        System.out.println("删除条数：" + rows);
    }

    /**
     * 根据map参数删除
     */
    @Test
    public void deleteByMap() {
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "刘明强");
        columnMap.put("age", 25);
        int rows = userMapper.deleteByMap(columnMap);
        System.out.println("删除条数：" + rows);
    }

    /**
     * 根据ID集合删除
     */
    @Test
    public void deleteBatchIds() {
        int rows = userMapper.deleteBatchIds(Arrays.asList(1321099776439136258L, 1321100685084811265L, 1321100748422971393L));
        System.out.println("删除条数：" + rows);
    }

    /**
     * 以条件构造器为参数的删除方法
     */
    @Test
    public void deleteBatchIds1() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(User::getAge, 27).or().gt(User::getAge, 41);
        int rows = userMapper.delete(lambdaQuery);
        System.out.println("删除条数：" + rows);
    }
}
