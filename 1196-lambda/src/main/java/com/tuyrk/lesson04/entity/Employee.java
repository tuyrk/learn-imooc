package com.tuyrk.lesson04.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 部门类
 *
 * @author tuyrk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Employee {
    private Long empNo;      // 职员编号
    private String empName;    // 职员名称
    private String nickname;   // 职员昵称
    private String job;        // 职员岗位
    private Long mgr;        // 上级编号
    private Date hirdate;    // 入伙时间
    private Integer salary;     // 薪水待遇
    private Integer comm;       // 奖金福利
    private Department dept;     // 所属部分
}
