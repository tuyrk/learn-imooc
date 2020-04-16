package com.damu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 部门类
 * @author 大牧
 * @version 1.0.0
 */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Accessors(chain = true)
public class Department<AllArgsConstructor> {
    private Long        deptNo;     //  部门编号
    private String      deptName;   //  部门名称
    private String      location;   //  部门所在地区

    public Long getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(Long deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}