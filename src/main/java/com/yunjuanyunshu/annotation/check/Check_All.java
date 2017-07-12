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
 * 检查所有内容
 */
public @interface Check_All {
}
