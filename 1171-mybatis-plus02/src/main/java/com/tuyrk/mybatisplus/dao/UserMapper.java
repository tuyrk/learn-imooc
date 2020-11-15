package com.tuyrk.mybatisplus.dao;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tuyrk.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface UserMapper extends CommMapper<User> {
    @InterceptorIgnore(tenantLine = "true")
    // @SqlParser(filter = true)
    @Select("select * from user ${ew.customSqlSegment}")
    List<User> mySelectList(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

    @InterceptorIgnore(tenantLine = "false")
    @Override
    User selectById(Serializable id);
}
