package com.tuyrk.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.quickstart.dao.UserMapper;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SelectWrapperTest extends ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    /**
     * 实体作为条件构造器构造方法的参数
     */
    @Test
    public void testWrapperEntity() {
        User user = new User();
        user.setName("刘红雨");
        user.setAge(32);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);

        queryWrapper.like("name", "雨").lt("age", 40);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * AllEq
     * - `params` : `key`为数据库字段名,`value`为字段值
     * - `null2IsNull` : 为`true`则在`map`的`value`为`null`时调用 isNull 方法,为`false`时则忽略`value`为`null`的
     * - `filter` : 过滤函数,是否允许字段传入比对条件中
     */
    @Test
    public void selectByWrapperAllEq() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("name", "王天风");
        params.put("age", null);

        queryWrapper.allEq(params, false);
        List<User> userList1 = userMapper.selectList(queryWrapper);
        userList1.forEach(System.out::println);

        queryWrapper.clear();

        queryWrapper.allEq((k, v) -> !Objects.equals("name", k), params);
        List<User> userList2 = userMapper.selectList(queryWrapper);
        userList2.forEach(System.out::println);
    }

    /**
     * selectMaps
     */
    @Test
    public void selectByWrapperMaps() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);

        List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * selectObjs
     * 只返回对象的第一个字段的值
     */
    @Test
    public void selectByWrapperObjs() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);

        List<Object> userList = userMapper.selectObjs(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * selectCount
     * 查询总记录数
     */
    @Test
    public void selectByWrapperCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("name", "雨").lt("age", 40);

        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println("总记录数：" + count);
    }

    /**
     * selectOne
     * 查询一条记录，可以为null，不能为多条数据
     */
    @Test
    public void selectByWrapperOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("name", "雨").lt("age", 40);

        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    /**
     * LambdaQueryWrapper
     */
    @Test
    public void selectLambda() {
        // LambdaQueryWrapper<User> lambdaQuery = new QueryWrapper<User>().lambda();
        // LambdaQueryWrapper<User> lambdaQuery = new LambdaQueryWrapper<>();
        // LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery(User.class);
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();

        lambdaQuery.like(User::getName, "雨").lt(User::getAge, 40);
        List<User> userList1 = userMapper.selectList(lambdaQuery);
        userList1.forEach(System.out::println);

        lambdaQuery.clear();

        lambdaQuery.likeRight(User::getAge, "王")
                .and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
        List<User> userList2 = userMapper.selectList(lambdaQuery);
        userList2.forEach(System.out::println);

        List<User> userList3 = new LambdaQueryChainWrapper<>(userMapper)
                .like(User::getName, "雨").ge(User::getAge, 20).list();
        userList3.forEach(System.out::println);
    }
}
