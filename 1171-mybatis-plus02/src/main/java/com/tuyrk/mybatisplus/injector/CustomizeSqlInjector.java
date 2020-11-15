package com.tuyrk.mybatisplus.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;
import com.tuyrk.mybatisplus.method.DeleteAllMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 自定义方法的注入
 *
 * @author tuyuankun
 */
@Component
public class CustomizeSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        // 必须要先获取默认的公用方法，不然默认的方法无法使用
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);

        // 删除所有数据
        methodList.add(new DeleteAllMethod());
        // 批量新增数据,自选字段 insert(排除逻辑删除字段)
        methodList.add(new InsertBatchSomeColumn(tableFieldInfo ->
                !tableFieldInfo.isLogicDelete()
                        && !Objects.equals("age", tableFieldInfo.getColumn())
        ));
        // 根据 id 逻辑删除数据,并带字段填充功能
        methodList.add(new LogicDeleteByIdWithFill());
        // 根据 ID 更新固定的那几个字段(但是不包含逻辑删除)
        methodList.add(new AlwaysUpdateSomeColumnById(tableFieldInfo ->
                !Objects.equals("name", tableFieldInfo.getColumn())
        ));

        return methodList;
    }
}
