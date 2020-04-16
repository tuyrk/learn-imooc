package com.imooc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Copyright (C), 2018-2019, copyright info. DAMU., Ltd.
 * FileName: com.imooc.pojo Emp
 * <p>职员类型</p>
 *
 * @author <a href="http://blog.csdn.net/muwenbin_flex">大牧莫邪</a>
 * @version 1.00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Emp {

    private Integer     empNo;          // 职员 编号
    private String      empName;        // 职员 名称
    private String      nickname;       // 职员 昵称
    private String      job;            // 职员 职务
    private Integer     mgr;            // 职员 上级
    private Date        hirdate;        // 职员  入伙时间
    private Integer     salary;         // 职员 薪水
    private Integer     comm;           // 职员 奖金
    private Integer     deptNo;         // 职员 部门编号
}
