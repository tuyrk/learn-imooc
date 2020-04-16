package com.tuyrk.lesson04.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 部门类
 *
 * @author tuyrk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Department {
    private Long deptNo;     //  部门编号
    private String deptName;   //  部门名称
    private String location;   //  部门所在地区

    public Department(Long deptNo) {
        this.deptNo = deptNo;
    }
}
