package com.yunjuanyunshu.annotation.check;

import java.lang.annotation.*;

/**
 * Created by zeng on 17-4-29.
 */
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
/**
 * 空值检查
 */
public @interface Check_Null {
    /**
     * 是否执行检查 默认值：检查
     * @return true：检查 | false：不检查
     */
    String value() default CheckEnum.CHECK;
}
