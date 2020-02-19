package com.fcant.java8.lambda.inter;

/**
 * Fun
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 14:51:50 2020/2/19/0019
 */
@FunctionalInterface
public interface Fun<T> {

    Integer getValue(T t);

}
