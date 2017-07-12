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
 * 长度检查
 */
public @interface Check_Length {

    String value() default CheckEnum.CHECK;
}
