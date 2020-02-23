package com.fcant.java8.annotation;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * TestAnnotation
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 22:15:22 2020/2/23/0023
 */
public class TestAnnotation {

    @Test
    public void annotationTest() throws NoSuchMethodException {
        Class<TestAnnotation> annotationClass = TestAnnotation.class;
        Method show = annotationClass.getMethod("show");
        Anno[] annotationsByType = show.getAnnotationsByType(Anno.class);
        for (Anno anno : annotationsByType) {
            System.out.println(anno.value());
        }
    }

    @Anno("Hello")
    @Anno("World")
    public void show(@Anno("abc") String name) {

    }
}
