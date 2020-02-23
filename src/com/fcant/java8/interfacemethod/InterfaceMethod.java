package com.fcant.java8.interfacemethod;

/**
 * InterfaceMethod
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 17:46:54 2020/2/23/0023
 */
public interface InterfaceMethod {
    default String getValue() {
        return "接口默认方法使用关键字default定义";
    }

    public static String getName() {
        return "接口静态方法使用关键字static定义";
    }
}
