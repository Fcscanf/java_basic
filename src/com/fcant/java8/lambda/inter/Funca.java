package com.fcant.java8.lambda.inter;

/**
 * Funca
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 20:32:15 2020/2/19/0019
 */
@FunctionalInterface
public interface Funca<T, R> {
    R op(T t1, T t2);
}
