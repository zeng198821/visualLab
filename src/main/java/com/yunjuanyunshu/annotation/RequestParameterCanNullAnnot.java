package com.yunjuanyunshu.annotation;

import java.lang.annotation.*;

/**
 * Created by zeng on 17-3-25.
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 请求参数是否可为空
 */
public @interface RequestParameterCanNullAnnot {

    /**
     * 请求参数是否可为空
     * @return True:可为空  Flase:不可为空
     */
     boolean value() default false;
}
