package com.tuyrk.mybatisplus.entity;

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
    @TableId
    private Long id;
    /**
     * 姓名
     */
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    /**
     * 年龄
     */
    @TableField(condition = "%s&lt;#{%s}", fill = FieldFill.UPDATE) // <
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    /**
     * 版本
     */
    @Version
    private Integer version;
    /**
     * 逻辑删除标识（0未删除，1已删除）
     */
    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
