package com.tuyrk.mybatisplus.test;

import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.quickstart.dao.UserMapper;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class InsertTest extends ApplicationTests {
    @Autowired
    private UserMapper UserMapper;

    /**
     * 测试时需要先修改数据库的字段
     * ## 更改表名
     * rename table user to mp_user;
     * ## 更改主键名称
     * alter table mp_user change id user_id bigint not null comment '主键';
     */
    @Test
    public void insert() {
        User user = new User(null, "刘明强", 31, null, 1088248166370832385L, LocalDate.now());
        int rows = UserMapper.insert(user);
        System.out.println("影响记录数：" + rows);
        Assertions.assertEquals(1, rows);
    }
}
