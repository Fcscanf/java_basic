package com.fcant.java8.lambda;

import com.fcant.java8.lambda.bean.Employee;
import com.fcant.java8.lambda.inter.ExPredicate;
import com.fcant.java8.lambda.inter.impl.FilterEmployeeByAge;
import com.fcant.java8.lambda.inter.impl.FilterEmployeeBySalary;
import org.junit.Test;

import java.util.*;

/**
 * AboutLambda
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 21:10:08 2020/2/18/0018
 */
public class AboutLambda {

    // 原来的匿名内部类
    @Test
    public void comparatorTest() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);
    }

    // Lambda表达式的写法
    @Test
    public void lambdaTest() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);
    }

    List<Employee> employees = Arrays.asList(
            new Employee("Fcant", 14, 99998.0),
            new Employee("Fcary", 10, 998.045),
            new Employee("Fcloi", 15, 934598.0),
            new Employee("Fcmop", 19, 56998.04),
            new Employee("Fcctr", 18, 945698.0),
            new Employee("Fcqyt", 17, 998.0645)
    );

    // 需求：获取当前公司中员工年龄大于17的员工的信息
    public List<Employee> filterEmployeeByAge(List<Employee> list) {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : list) {
            if (employee.getAge() > 16) {
                employees.add(employee);
            }
        }
        return employees;
    }

    // 需求：获取当前公司中员工薪水大于5000的员工的信息
    public List<Employee> filterEmployeeBySalary(List<Employee> list) {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : list) {
            if (employee.getSalary() > 5000) {
                employees.add(employee);
            }
        }
        return employees;
    }

    // 对上面的需求进行优化
    // 优化方式一:策略设计模式
    public List<Employee> filterEmployee(List<Employee> list, ExPredicate<Employee> exPredicate) {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : list) {
            if (exPredicate.is(employee)) {
                employees.add(employee);
            }
        }
        return employees;
    }

    @Test
    public void Test() {
        // List<Employee> employees = filterEmployeeByAge(this.employees);
        List<Employee> employeeListByAge = filterEmployee(this.employees, new FilterEmployeeByAge());
        for (Employee employee : employeeListByAge) {
            System.out.println(employee);
        }

        System.out.println("----------------根据薪资过滤---------------");

        List<Employee> employeeListBySalary = filterEmployee(this.employees, new FilterEmployeeBySalary());
        for (Employee employee : employeeListBySalary) {
            System.out.println(employee);
        }

        // 优化方式二：匿名内部类
        System.out.println("-------------匿名内部类-------------");
        List<Employee> filterEmployee = filterEmployee(this.employees, new ExPredicate<Employee>() {
            @Override
            public boolean is(Employee employee) {
                return employee.getSalary() < 5000;
            }
        });
        for (Employee employee : filterEmployee) {
            System.out.println(employee);
        }

        // 优化方式三：Lambda表达式
        System.out.println("-------------Lambda表达式------------");
        List<Employee> employeesByLambda = filterEmployee(this.employees, (e) -> e.getSalary() <= 5000);
        employeesByLambda.forEach(System.out::println);

        // 优化方式四：Stream流
        System.out.println("-----------------Stream流过滤---------------");
        employees.stream()
                .filter((e) -> e.getSalary() >= 5000)
                .limit(2)
                .forEach(System.out::println);
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
    }
}
