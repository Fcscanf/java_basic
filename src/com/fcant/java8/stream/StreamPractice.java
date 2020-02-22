package com.fcant.java8.stream;

import com.fcant.java8.lambda.bean.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * StreamPractice
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 17:33:04 2020/2/22/0022
 */
public class StreamPractice {

    /**
     * 给定一个数字列表，如何返回一个由每个数的平方构成的列表，给定[1, 2, 3, 4, 5]
     */
    @Test
    public void areaTest() {
        Integer[] nums = new Integer[]{1, 2, 3, 4, 5};
        Arrays.stream(nums)
                .map(x -> x*x)
                .forEach(System.out::println);
    }

    List<Employee> employees = Arrays.asList(
            new Employee("Fcant", 14, 99998.0, Employee.Status.BUSY),
            new Employee("Fcary", 10, 998.045, Employee.Status.VOCATION),
            new Employee("Fcloi", 15, 934598.0, Employee.Status.FREE),
            new Employee("Fcmop", 19, 56998.04, Employee.Status.BUSY),
            new Employee("Fcctr", 18, 945698.0, Employee.Status.BUSY),
            new Employee("Fcctr", 18, 945698.0, Employee.Status.FREE),
            new Employee("Fcctr", 18, 945698.0, Employee.Status.VOCATION),
            new Employee("Fcqyt", 17, 998.0645, Employee.Status.FREE)
    );

    /**
     * 怎样通过Map或Reduce计算流中有多少Employee
     */
    @Test
    public void countEmployeeTest() {
        Optional<Integer> optionalInteger = employees.stream()
                .map(employee -> 1)
                .reduce(Integer::sum);
        System.out.println(optionalInteger.get());
    }
}
