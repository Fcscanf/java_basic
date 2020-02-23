package com.fcant.java8.annotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * annotation
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 22:10:52 2020/2/23/0023
 */
@Repeatable(Annotations.class)
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Anno {
    String value() default "Fcant";
}
