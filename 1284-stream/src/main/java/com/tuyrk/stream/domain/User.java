package com.tuyrk.stream.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mooc_users")
public class User implements Serializable {

    private static final long serialVersionUID = 5089511308631056603L;

    /**
     * 自增长 ID，唯一标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户名
     */
    @Column(length = 50, unique = true, nullable = false)
    private String username;
    /**
     * 手机号
     */
    @Column(length = 11, unique = true, nullable = false)
    private String mobile;
    /**
     * 姓名
     */
    @Column(length = 50)
    private String name;
    /**
     * 是否激活，默认激活
     */
    @Builder.Default
    @Column(nullable = false)
    private boolean enabled = true;
    /**
     * 密码哈希
     */
    @JsonIgnore
    @Column(name = "password_hash", length = 80, nullable = false)
    private String password;
    /**
     * 电邮地址
     */
    @Column(unique = true, nullable = false)
    private String email;
    private int age;
    @Getter
    @Transient
    public List<String> roles;
}
