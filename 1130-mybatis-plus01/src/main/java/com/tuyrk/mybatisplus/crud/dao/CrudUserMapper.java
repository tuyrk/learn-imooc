package com.tuyrk.mybatisplus.crud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuyrk.mybatisplus.crud.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrudUserMapper extends BaseMapper<User> {
}
