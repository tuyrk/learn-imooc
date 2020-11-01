package com.tuyrk.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import com.tuyrk.mybatisplus.quickstart.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class ServiceTest extends ApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    public void getOne() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.gt(User::getAge, 25);
        User user1 = userService.getOne(lambdaQuery, false);
        System.out.println(user1);
        User user2 = userService.getOne(lambdaQuery);
        System.out.println(user2);
    }

    @Test
    public void saveBatch() {
        User user1 = new User();
        user1.setName("徐丽3");
        user1.setAge(30);
        User user2 = new User();
        user2.setId(1322871394721493004L);
        user2.setName("徐力2");
        user2.setAge(29);
        List<User> userList = Arrays.asList(user1, user2);

        boolean saveBatch = userService.saveOrUpdateBatch(userList);
        System.out.println("saveBatch = " + saveBatch);
    }

    @Test
    public void queryChain() {
        List<User> userList = userService.lambdaQuery()
                .gt(User::getAge, 25).like(User::getName, "雨")
                .list();
        userList.forEach(System.out::println);
    }

    @Test
    public void updateChain() {
        boolean update = userService.lambdaUpdate()
                .ge(User::getAge, 25)
                .set(User::getAge, 26)
                .update();
        System.out.println("update = " + update);
    }

    @Test
    public void removeChain() {
        boolean remove = userService.lambdaUpdate()
                .ge(User::getAge, 25)
                .remove();
        System.out.println("remove = " + remove);
    }
}
