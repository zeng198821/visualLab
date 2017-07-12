package com.yunjuanyunshu.annotation.check;

import java.lang.annotation.*;

/**
 * Created by zeng on 17-4-29.
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
/**
 * 函数执行检查标识，用于检查类中标识具体检查执行函数
 */
public @interface Check_Ext {

    /**
     * 是否执行检查 默认值：检查
     * @return true：检查 | false：不检查
     */
    String value() default CheckEnum.CHECK;
}
