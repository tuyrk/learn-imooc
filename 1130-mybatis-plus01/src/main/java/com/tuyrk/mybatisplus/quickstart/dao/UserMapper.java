package com.tuyrk.mybatisplus.quickstart.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user ${ew.customSqlSegment}")
    List<User> selectAllByAnnotation(@Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
                                             Wrapper<User> wrapper);

    List<User> selectAllByXml(@Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
                                      Wrapper<User> wrapper);

    IPage<User> selectUserPage(Page<User> page, @Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
            Wrapper<User> wrapper);
}
