package com.tuyrk.mybatisplus.test;

import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ActiveRecordTest extends ApplicationTests {
    @Test
    public void insert() {
        User user = new User();
        user.setName("张草");
        user.setAge(24);
        user.setEmail("zc@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDate.now());
        boolean insert = user.insert();
        System.out.println("insert = " + insert);
        System.out.println(user.getId());
    }

    @Test
    public void selectById1() {
        User user = new User();
        User userSelect = user.selectById(1322869223491497986L);
        System.out.println(user == userSelect);
        System.out.println("userSelect = " + userSelect);
    }

    @Test
    public void selectById2() {
        User user = new User();
        user.setId(1322869223491497986L);
        User userSelect = user.selectById();
        System.out.println(user == userSelect);
        System.out.println("userSelect = " + userSelect);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setId(1322869223491497986L);
        user.setName("张草草");
        boolean update = user.updateById();
        System.out.println("update = " + update);
    }

    @Test
    public void deleteById() {
        User user = new User();
        user.setId(1322869223491497986L);
        boolean update = user.deleteById();
        // boolean update = user.deleteById(1322869223491497986L);
        System.out.println("update = " + update);
    }

    /**
     * user有id则执行更新操作，没有id则执行插入操作
     */
    @Test
    public void insertOrUpdate() {
        User user = new User();
        user.setId(1322871394721492993L);
        user.setName("张强");
        user.setAge(24);
        user.setEmail("zq@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDate.now());
        boolean insert = user.insertOrUpdate();
        System.out.println("insert = " + insert);
    }
}
