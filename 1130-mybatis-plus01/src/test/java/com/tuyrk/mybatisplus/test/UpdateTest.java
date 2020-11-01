package com.tuyrk.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.quickstart.dao.UserMapper;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdateTest extends ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据ID更新
     */
    @Test
    public void updateById() {
        User user = new User();
        user.setId(1088248166370832385L); // where
        user.setAge(26); // set
        user.setEmail("wtf2@baomidou.com"); // set
        int rows = userMapper.updateById(user);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 以条件构造器作为参数的更新方法
     */
    @Test
    public void updateByWrapper() {
        UpdateWrapper<User> updateWrapper = Wrappers.update(); // where
        updateWrapper.eq("name", "李艺伟").eq("age", 28);

        User user = new User(); // set
        user.setEmail("lyw2020@baomidou.com");
        user.setAge(29);

        int rows = userMapper.update(user, updateWrapper);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 以条件构造器的构造函数作为参数的更新方法
     */
    @Test
    public void updateByWrapperCon() {
        User whereUser = new User();
        whereUser.setName("李艺伟");
        UpdateWrapper<User> updateWrapper = Wrappers.update(whereUser); // where
        updateWrapper.eq("name", "李艺伟").eq("age", 28);

        User user = new User(); // set
        user.setEmail("lyw2020@baomidou.com");
        user.setAge(29);

        int rows = userMapper.update(user, updateWrapper);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 条件构造器中set方法使用
     */
    @Test
    public void updateByWrapperSet() {
        UpdateWrapper<User> updateWrapper = Wrappers.update();
        updateWrapper.eq("name", "李艺伟").eq("age", 29) // where
                .set("age", 30); // set
        int rows = userMapper.update(null, updateWrapper);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 条件构造器Lambda条件参数
     */
    @Test
    public void updateByWrapperLambda() {
        LambdaUpdateWrapper<User> lambdaUpdate = Wrappers.lambdaUpdate();
        lambdaUpdate.eq(User::getName, "李艺伟").eq(User::getAge, 30)
                .set(User::getAge, 31);
        int rows = userMapper.update(null, lambdaUpdate);
        System.out.println("影响记录数：" + rows);
    }

    /**
     * 条件构造器Lambda条件参数链式更新调用
     */
    @Test
    public void updateByWrapperLambdaChain() {
        boolean rows = ChainWrappers.lambdaUpdateChain(userMapper)
                .eq(User::getName, "李艺伟").eq(User::getAge, 30)
                .set(User::getAge, 31)
                .update();
        System.out.println("影响记录数：" + rows);
    }
}
