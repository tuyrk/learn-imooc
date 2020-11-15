package com.tuyrk.mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class MybatisPlusConfig {
    /**
     * 逻辑删除
     */
    /*@Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector();
    }*/

    /**
     * mybatis plus配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 乐观锁
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 分页配置
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 多租户
        /*mybatisPlusInterceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public String getTenantIdColumn() {
                return "manager_id";
            }

            @Override
            public Expression getTenantId() {
                return new LongValue(1088248166370832385L);
            }

            *//**
         * 默认返回 false 表示所有表都需要拼多租户条件
         * @param tableName 表名
         * @return 是否过滤租户信息
         *//*
            @Override
            public boolean ignoreTable(String tableName) {
                return Objects.equals("role", tableName);
            }
        }));*/
        // 动态表名
        mybatisPlusInterceptor.addInnerInterceptor(new DynamicTableNameInnerInterceptor(new HashMap<String, TableNameHandler>(2) {
            private static final long serialVersionUID = -5574528566486194648L;

            {
                // put("user", (sql, tableName) -> tableName + "_" + System.currentTimeMillis());
                // put("user", null);
                put(null, null);
            }
        }));

        return mybatisPlusInterceptor;
    }
}
