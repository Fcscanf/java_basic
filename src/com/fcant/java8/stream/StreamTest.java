package com.fcant.java8.stream;

import com.fcant.java8.lambda.bean.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * StreamTest
 * <p>
 * encoding:UTF-8
 *
 * 一、Stream的三个操作步骤
 * 1.创建Stream
 * 2.中间操作
 * 3.终止操作（终端操作）
 *
 * @author Fcant 下午 17:18:28 2020/2/20/0020
 */
public class StreamTest  {

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

    // 终止操作

    /**
     * 收集
     * collect-将流转换为其他形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
     */

    @Test
    public void streamCollectTest() {
        employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println("----------------------");
        employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet())
                .forEach(System.out::println);

        System.out.println("-----------------------");
        employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new))
                .forEach(System.out::println);

        // 总数
        Long count = employees.stream()
                .collect(Collectors.counting());
        System.out.println(count);

        // 平均值
        Double avg = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        // 总和
        Double sum = employees.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        // 最大值
        Optional<Employee> optionalEmployee = employees.stream()
                .collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(optionalEmployee.get());

        // 最小值
        Optional<Double> min = employees.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());

        // 分组
        Map<Employee.Status, List<Employee>> group = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        Employee.Status[] values = Employee.Status.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println(values[i]);
            group.get(values[i]).forEach(System.out::println);
        }

        // 多级分组
        System.out.println("-----------多级分组----------");
        Map<Employee.Status, Map<String, List<Employee>>> emp = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
                    if (((Employee) e).getAge() <= 15) {
                        return "初中生";
                    } else if (((Employee) e).getAge() <= 17) {
                        return "高中生";
                    } else {
                        return "大学生";
                    }
                })));
        System.out.println(emp);
        for (int i = 0; i < values.length; i++) {
            System.out.println(values[i]);
            group.get(values[i]).forEach(System.out::println);
        }

        // 分片、分区
        Map<Boolean, List<Employee>> collect = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() > 5000));
        System.out.println(collect);

        // 分组函数的另外一种获取使用
        DoubleSummaryStatistics empSalary = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println("员工薪资总和为：" + empSalary.getSum());
        System.out.println("员工薪资有 " + empSalary.getCount() + " 条");
        System.out.println("员工薪资均值为：" + empSalary.getAverage());
        System.out.println("员工薪资最大值为：" + empSalary.getMax());
        System.out.println("员工薪资最小值为：" + empSalary.getMin());

        String empName = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining("," , "==>", "<=="));
        System.out.println(empName);
    }

    /**
     * 规约
     * reduce(T identity, BinaryOperator)/reduce(BinaryOperator)-可以将流中元素反复结合起来，得到一个值
     */

    @Test
    public void streamReduceTest() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println("该数组之和为：" + sum);

        System.out.println("----------------------");

        Optional<Double> optionalDouble = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println("员工的工资总和为：" + optionalDouble.get());
    }

    /**
     * 查找与匹配
     * allMatch-检查是否匹配所有元素
     * anyMatch-检查是否至少匹配一个元素
     * noneMatch-检查是否没有匹配所有元素
     * findFirst-返回第一个元素
     * findAny-返回当前流中任意元素
     * count-返回流中元素的总个数
     * max-返回流中最大值
     * min-返回流中最小值
     */

    @Test
    public void streamFindTest() {
        boolean allMatch = employees.stream()
                .allMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(allMatch);

        boolean anyMatch = employees.stream()
                .anyMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(anyMatch);

        boolean noneMatch = employees.stream()
                .noneMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(noneMatch);

        Optional<Employee> firstEmployee = employees.stream()
                .sorted((e1, e2) -> -Double.compare(e1.getSalary(), e2.getSalary()))
                .findFirst();
        System.out.println(firstEmployee.get());

        Optional<Employee> optionalEmployee = employees.stream()
                .filter(e -> e.getStatus().equals(Employee.Status.FREE))
                .findAny();
        System.out.println(optionalEmployee.get());

        long count = employees.stream()
                .count();
        System.out.println(count);

        Optional<Employee> maxEmpSalary = employees.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(maxEmpSalary.get());

        Optional<Double> min = employees.stream()
                .map(Employee::getSalary)
                .min(Double::compare);
        System.out.println(min.get());
    }

    // 中间操作

    /**
     * 排序
     * sorted()-自然排序(Comparable)
     * sorted(Comparator com)-定制排序(Comparator)
     */
    @Test
    public void sortStreamTest() {
        System.out.println("-----------sort()自然排序------------");
        List<String> list = Arrays.asList("ccc", "ddd", "aaa", "bbb", "eee");
        list.stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println("-----------sort()定制排序------------");
        employees.stream()
                .sorted((e1, e2) -> {
                    if (e1.getAge().equals(e2.getAge())) {
                        return e1.getName().compareTo(e2.getName());
                    } else {
                        return e1.getAge().compareTo(e2.getAge());
                    }
                }).forEach(System.out::println);
    }

    /**
     * 映射
     * map-接收Lambda，将元素转换成其他形式或提取信息，接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素
     * flatMap-接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void refTest() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        list.stream()
                .map(x -> x.toUpperCase())
                .forEach(System.out::println);
        System.out.println("-------------员工姓名提取------------");
        employees.stream()
                .map(x -> x.getName())
                .forEach(System.out::println);

        System.out.println("-------------通过map将流整合成一个流------------");
        Stream<Stream<Character>> streamStream = list.stream()
                .map(StreamTest::filterCharacter);
        streamStream.forEach(sm -> sm.forEach(System.out::println));

        System.out.println("----------通过flatMap进行流的整合-----------");
        list.stream()
                .flatMap(StreamTest::filterCharacter)
                .forEach(System.out::println);
    }

    public static Stream<Character> filterCharacter(String s) {
        List<Character> characterList = new ArrayList<>();
        for (Character character : s.toCharArray()) {
            characterList.add(character);
        }
        return characterList.stream();
    }

    /**
     * 筛选与切片
     * filter-接收Lambda，从流中排除某些元素
     * limit-截断流，使其元素不超过给定数量
     * skip-跳过元素，返回一个扔掉了前n个元素的流，若流中元素不足n个，则返回一个空流。与limit(n)互补
     * distinct-筛选，通过流所生成元素的hashCode()和equals()去除重复元素
     */
    @Test
    public void streamSelectOpTest() {
        System.out.println("--------------filter-----------");
        employees.stream()
                .filter(employee -> {
                    System.out.println("Steam API 中间操作");
                    return employee.getAge() > 15;
                })
                .forEach(System.out::println);

        System.out.println("--------------limit-----------");
        employees.stream()
                .filter(employee -> {
                    System.out.println("短路！-使用limit找到符合条件的数据后面的不再迭代遍历");
                    return employee.getSalary() > 5000;
                })
                .limit(2)
                .forEach(System.out::println);

        System.out.println("--------------skip-----------");
        employees.stream()
                .filter(employee -> {
                    System.out.println("迭代遍历所有");
                    return employee.getSalary() > 5000;
                })
                .skip(2)
                .forEach(System.out::println);

        // 使用distinct去重时需要重写hashCode()方法和equals()方法
        System.out.println("--------------distinct-----------");
        employees.stream()
                .filter(employee -> {
                    System.out.println("迭代遍历所有");
                    return employee.getSalary() > 5000;
                })
                .skip(1)
                .distinct()
                .forEach(System.out::println);
    }


    // 创建Stream

    @Test
    public void streamTest() {
        // 1.可以通过Collection 系列集合提供的stream() 或 paralleStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();

        // 2.通过Arrays中的静态方法stream()获取数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> employeeStream = Arrays.stream(employees);

        // 3.通过Stream类中的静态方法of()
        Stream<String> stringStream = Stream.of("aa", "bb", "cc");

        // 4.创建无限流
        // 迭代
        Stream.iterate(0, (x) -> x + 2)
                .limit(100)
                .forEach(System.out::println);

        // 生成
        Stream.generate(() -> Math.random())
                .limit(5)
                .forEach(System.out::println);
    }
}
