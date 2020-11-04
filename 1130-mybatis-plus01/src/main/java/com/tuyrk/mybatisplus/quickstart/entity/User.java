package com.tuyrk.mybatisplus.quickstart.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {
    private static final long serialVersionUID = 2973225901816678814L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 姓名
     */
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    /**
     * 年龄
     */
    @TableField(condition = "%s&lt;#{%s}") // <
    private Integer age;
    /**
     * 邮箱
     */
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String email;
    /**
     * 直属上级
     */
    private Long managerId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
