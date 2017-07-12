package com.yunjuanyunshu.annotation;

import java.lang.annotation.*;

/**
 * Created by zeng on 17-3-25.
 */
@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnInfoAnno {

    String value() default "";

    /**
     * 字段名称
     * @return 字段名称
     */
    String columnName() default "";

    /**
     * 字段注释名称
     * @return 字段注释名称
     */
    String columnDesc() default "";

    /**
     * 字段字节长度
     * @return 字段字节长度
     */
    int columnBytes() default 0;

    /**
     * 字段最大值（数字）
     * @return 字段最大值（数字）
     */
    int columnRangeMax() default Integer.MAX_VALUE;
    /**
     * 字段最小值（数字）
     * @return 字段最小值（数字）
     */
    int columnRangeMin() default Integer.MAX_VALUE;

    /**
     * 字段是否允许为空
     * @return 字段是否允许为空
     */
    boolean canNull() default true;
}
