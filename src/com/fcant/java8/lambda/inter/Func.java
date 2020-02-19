package com.fcant.java8.lambda.inter;

/**
 * Func
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 20:24:19 2020/2/19/0019
 */
@FunctionalInterface
public interface Func<T> {
    String getValue(T t);
}
