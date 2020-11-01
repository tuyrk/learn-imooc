package com.tuyrk.mybatisplus.crud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@TableName("mp_user")
public class User {
    /**
     * 主键
     */
    // @TableId("user_id")
    // private Long id;
    @TableId
    private Long userId;
    /**
     * 姓名
     */
    @TableField("name")
    private String realName;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 直属上级
     */
    private Long managerId;
    /**
     * 创建时间
     */
    private LocalDate createTime;
    /**
     * 备注
     */
    @TableField(exist = false)
    private String remark;
}
