package com.fcant.java8.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Annotations
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 22:16:43 2020/2/23/0023
 */
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Annotations {
    Anno[] value();
}
