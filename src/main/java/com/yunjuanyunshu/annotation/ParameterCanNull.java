package com.yunjuanyunshu.annotation;

import com.yunjuanyunshu.annotation.check.CheckEnum;

import java.lang.annotation.*;

/**
 * Created by zeng on 17-4-29.
 */


@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
/**
 * 参数是非可为空
 */
public @interface ParameterCanNull {

    /**
     * 是否执行检查 默认值：可为空
     * @return true：可为空 | false：不可为空
     */
    String value() default CheckEnum.CHECK;
}
