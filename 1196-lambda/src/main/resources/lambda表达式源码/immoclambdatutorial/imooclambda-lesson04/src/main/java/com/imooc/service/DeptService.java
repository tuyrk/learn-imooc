package com.imooc.service;

import com.imooc.mapper.DeptMapper;
import com.imooc.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (C), 2018-2019, copyright info. DAMU., Ltd.
 * FileName: com.imooc.service DeptService
 * <p>TODO</p>
 *
 * @author <a href="http://blog.csdn.net/muwenbin_flex">大牧莫邪</a>
 * @version 1.00
 */
@Service
public class DeptService {

    @Autowired
    private DeptMapper deptMapper;

    public List<Dept> findAllDept() {
        return deptMapper.findAll();
    }
}
