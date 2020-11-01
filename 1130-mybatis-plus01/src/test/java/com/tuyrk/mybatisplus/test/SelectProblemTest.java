package com.tuyrk.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.quickstart.dao.UserMapper;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SelectProblemTest extends ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    /**
     * 名字中包含雨并且年龄小于40
     * name like '%雨%' and age<40
     */
    @Test
    public void selectList1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // QueryWrapper<Object> queryWrapper = Wrappers.query();

        queryWrapper.like("name", "雨").lt("age", 40);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
     * name like '%雨%' and age between 20 and 40 and email is not null
     */
    @Test
    public void selectList2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("name", "雨").between("age", 20, 40).isNotNull("email");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     * name like '王%' or age>=25 order by age desc,id asc
     */
    @Test
    public void selectList3() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.likeRight("name", "王").or().ge("age", 25).orderByDesc("age").orderByAsc("id");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 创建日期为2019年2月14日并且直属上级为名字为王姓
     * date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
     */
    @Test
    public void selectList4() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", "2020-10-27")
                .inSql("manager_id", "select id from user where name like '王%'");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字为王姓并且（年龄小于40或邮箱不为空）
     * name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void selectList5() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.likeRight("name", "王")
                .and(qw -> qw.lt("age", 40).or().isNotNull("email"));

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     * name like '王%' or (age<40 and age>20 and email is not null)
     */
    @Test
    public void selectList6() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.likeRight("name", "王")
                .or(qw -> qw.lt("age", 40).gt("age", 20).isNotNull("email"));

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * （年龄小于40或邮箱不为空）并且名字为王姓
     * (age<40 or email is not null) and name like '王%'
     */
    @Test
    public void selectList7() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.nested(qw -> qw.lt("age", 40).or().isNotNull("email"))
                .likeRight("name", "王");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 年龄为30、31、34、35
     * age in (30、31、34、35)
     */
    @Test
    public void selectList8() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35));

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 只返回满足条件的其中一条语句即可
     * limit 1
     */
    @Test
    public void selectList9() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35)).last("limit 1");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字中包含雨并且年龄小于40(需求1加强版)
     * select id, name from user
     * where name like '%雨%' and age < 40;
     * <p>
     * select id, name, age, email
     * from user
     * where name like '%雨%' and age < 40;
     */
    @Test
    public void selectList10() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);
        queryWrapper.select(User.class, info -> {
            return !Objects.equals("manager_id", info.getColumn())
                    && !Objects.equals("create_time", info.getColumn());
        }).like("name", "雨").lt("age", 40);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。并且只取年龄总和小于500的组。
     * <p>
     * select avg(age) avg_age, min(age) min_age, max(age) max_age
     * from user
     * group by manager_id
     * having sum(age) < 500;
     */
    @Test
    public void selectList11() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id").having("sum(age) < {0}", 500);

        List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
        userList.forEach(System.out::println);
    }
}
