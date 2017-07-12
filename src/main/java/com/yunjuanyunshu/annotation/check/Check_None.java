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
 * 不做任何检查
 */
public @interface Check_None {
}
