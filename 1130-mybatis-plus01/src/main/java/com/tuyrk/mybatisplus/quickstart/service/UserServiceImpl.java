package com.tuyrk.mybatisplus.quickstart.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuyrk.mybatisplus.quickstart.dao.UserMapper;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
