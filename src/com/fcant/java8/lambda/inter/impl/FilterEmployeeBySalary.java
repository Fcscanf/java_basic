package com.fcant.java8.lambda.inter.impl;

import com.fcant.java8.lambda.bean.Employee;
import com.fcant.java8.lambda.inter.ExPredicate;

/**
 * FilterEmployeeBySalary
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 上午 10:39:30 2020/2/19/0019
 */
public class FilterEmployeeBySalary implements ExPredicate<Employee> {
    @Override
    public boolean is(Employee employee) {
        return employee.getSalary() > 5000;
    }
}
