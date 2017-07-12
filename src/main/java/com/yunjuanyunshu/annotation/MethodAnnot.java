/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yunjuanyunshu.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ajax 请求方法体注解
 *
 * @author zeng
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodAnnot {

    /**
     * Ajax请求名称注解，默认值为函数名称
     *
     * @return
     */
    public String value() default Constants.EXT_VALIDATOR_MEDTHOD_NAME;

}
