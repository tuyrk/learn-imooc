package com.imooc.util;

import com.imooc.pojo.Dept;

/**
 * Copyright (C), 2018-2019, copyright info. DAMU., Ltd.
 * FileName: com.damu.pojo Article
 * <p>部门查询相关-策略接口</p>
 *
 * @author <a href="http://blog.csdn.net/muwenbin_flex">大牧莫邪</a>
 * @version 1.00
 */
public interface DeptStrategy {

    /**
     * 部门查询策略
     * @param dept 待测试的部门数据
     * @return 返回测试结果
     */
    Boolean test(Dept dept);
}
