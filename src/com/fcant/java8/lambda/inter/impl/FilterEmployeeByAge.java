package com.fcant.java8.lambda.inter.impl;

import com.fcant.java8.lambda.bean.Employee;
import com.fcant.java8.lambda.inter.ExPredicate;

/**
 * FilterEmployeeByAge
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 上午 10:28:35 2020/2/19/0019
 */
public class FilterEmployeeByAge implements ExPredicate<Employee> {

    @Override
    public boolean is(Employee employee) {
        return employee.getAge() > 15;
    }
}
