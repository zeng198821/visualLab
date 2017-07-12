package com.yunjuanyunshu.annotation;

/**
 * Created by zeng on 2017-7-12.
 */

import java.lang.annotation.*;

/**
 * Ajax 请求方法体注解
 *
 * @author zeng
 */
@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface TcpPkgAnno {

    /**
     * Ajax请求名称注解，默认值为函数名称
     *
     * @return
     */
    String value() default "";
    /**
     * 包体字段名称
     * @return 包体字段名称
     */
    String pkgName() default "";

    /**
     * 包体字段索引
     * @return 包体字段索引
     */
    int pkgIdx() default 0;

    /**
     * 包体字段类型
     * @return 包体字段类型
     */
    String pkgType() default "";






}
