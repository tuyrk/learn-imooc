package com.damu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 * 部门类
 * @author 大牧
 * @version 1.0.0
 */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Accessors(chain = true)
public class Employee {
    private Long            empNo;      // 职员编号
    private String          empName;    // 职员名称
    private String          nickname;   // 职员昵称
    private String          job;        // 职员岗位
    private Long            mgr;        // 上级编号
    private Date            hirdate;    // 入伙时间
    private Integer         salary;     // 薪水待遇
    private Integer         comm;       // 奖金福利
    private Department      dept;     // 所属部分


    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Long getMgr() {
        return mgr;
    }

    public void setMgr(Long mgr) {
        this.mgr = mgr;
    }

    public Date getHirdate() {
        return hirdate;
    }

    public void setHirdate(Date hirdate) {
        this.hirdate = hirdate;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getComm() {
        return comm;
    }

    public void setComm(Integer comm) {
        this.comm = comm;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
}
