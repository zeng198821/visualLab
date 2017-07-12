package com.yunjuanyunshu.annotation;

import java.lang.annotation.*;

/**
 * Created by zeng on 17-3-25.
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityInfoAnno {

    String value() default "";

    /**
     * 表名称
     * @return 表名称
     */
    String tableName() default "";

    /**
     * 表注释名称
     * @return 表注释名称
     */
    String tableDesc() default "";

}
