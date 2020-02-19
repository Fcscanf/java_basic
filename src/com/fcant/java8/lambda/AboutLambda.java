package com.fcant.java8.lambda;

import com.fcant.java8.lambda.bean.Employee;
import com.fcant.java8.lambda.inter.ExPredicate;
import com.fcant.java8.lambda.inter.Fun;
import com.fcant.java8.lambda.inter.Func;
import com.fcant.java8.lambda.inter.Funca;
import com.fcant.java8.lambda.inter.impl.FilterEmployeeByAge;
import com.fcant.java8.lambda.inter.impl.FilterEmployeeBySalary;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * AboutLambda
 * <p>
 * encoding:UTF-8
 *
 * 一、Lambda表达式的基础语法：Java8中引入了一个新的操作符"->"该操作符称为箭头操作符或者Lambda操作符
 *      箭头操作符将Lambda表达式拆分成两部分
 * 1.左侧：Lambda表达式的参数列表
 * 2.右侧：Lambda表达式中所需执行的功能，即Lambda体
 *
 * 语法格式一：无参数，无返回值
 *      () -> System.out.println("Hello Lambda!")
 *
 * 语法格式二：有一个参数，无返回值
 *      (x) -> System.out.println(x);
 *
 * 语法格式三：有一个参数，无返回值时小括号可以省略不写
 *      x -> System.out.println(x);
 *
 * 语法格式四：有一个或多个参数，有返回值，有多条处理语句
 *      Comparator<Integer> comparator = (x, y) -> {
 *          System.out.println("函数式接口");
 *          return Integer.compare(x, y);
 *      };
 *
 * 语法格式五：若Lambda体中只有一条语句，return和大括号都可以不写
 *      (x, y) -> Integer.compare(x, y);
 *
 * 语法格式六：Lambda表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
 *      (Integer x, Integer y) -> Integer.compare(x, y);
 *
 * 二、Lambda表达式需要“函数式接口”的支持
 * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。
 *      可以使用注解@FunctionalInterface 添加在该接口类上进行修饰
 *      可以检查是否是函数式接口
 *
 * 三、Java8内置四大核心函数式接口
 * Consumer<T> :消费型接口
 *      void accept(T t);
 *
 * Supplier<T> :供给型接口
 *      T get();
 *
 * Function<T, R> :函数型接口
 *      R apply(T t);
 *
 * Predicate<T> :断言型接口
 *      boolean test(T t);
 *
 *
 *
 *
 * @author Fcant 下午 21:10:08 2020/2/18/0018
 */
public class AboutLambda {

    // Java8四大核心函数接口
    // 1.Consumer<T> :消费型接口
    public void happyTest(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    @Test
    public void consumerTest() {
        happyTest(1000d, (x) -> System.out.println("本次消费：" + x + "元"));
    }

    // 2.Supplier<T> :供给型接口
    public List<Integer> getNumList(int n, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Integer num = supplier.get();
            list.add(num);
        }
        return list;
    }

    @Test
    public void supplierTest() {
        getNumList(10, () -> (int)(Math.random() * 100)).forEach(System.out::println);
    }

    // 3.Function<T, R> :函数型接口
    public String strHandler(String s, Function<String, String> function) {
        return function.apply(s);
    }

    @Test
    public void strTest() {
        System.out.println(strHandler("gh", (s) -> s.toUpperCase()));
    }

    // 4.Predicate<T> :断言型接口
    public List<String> filterStr(List<String> list, Predicate<String> predicate) {
        List<String> strings = new ArrayList<>();
        for (String s : list) {
            if (predicate.test(s)) {
                strings.add(s);
            }
        }
        return strings;
    }

    @Test
    public void strFilterTest() {
        List<String> stringList = Arrays.asList("Hello", "Fcant", "Lambda", "ww", "ok");
        filterStr(stringList, (s) -> s.length() > 3).forEach(System.out::println);
    }

    // Lambda练习小例子

    // 案例1
    // 调用Collection.sort(),通过定制排序比较两个Employee（先按年龄比，年龄相同按姓名比，使用Lambda作为参数传递）
    @Test
    public void empTest() {
        Collections.sort(employees, (e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return -Integer.compare(e1.getAge(), e2.getAge());
            }
        });
        employees.forEach(System.out::println);
    }

    // 案例二
    // ①声明函数式接口，接口中声明抽象方法，public String getValue（String str）；
    // ②声明类TestLambda，类中编写方法使用接口作为参数，将一个字符串转换成大写，并作为方法的返回值。
    // ③再将一个字符串的第二个和第四个索引位置进行截取子串
    @Test
    public void functionTest() {
        System.out.println(option("agHdfC", (x) -> x.toUpperCase()));
        System.out.println(option("hsafdsfh", (x) -> x.substring(2, 5)));
    }

    public String option(String s, Func<String> func) {
        return func.getValue(s);
    }

    // 案例三
    // ①声明一个带两个泛型的函数式接口，泛型类型为<T, R>,T为参数，R为返回值
    // ②接口中声明对应的抽象方法
    // ③在TestLambda类中声明方法，使用接口作为参数，计算两个long型参数的和
    // ④再计算两个long型参数的乘积
    @Test
    public void trTest() {
        System.out.println(ops(12l, 56l, (t1, t2) -> t1 + t2));
        System.out.println(ops(12l, 56l, (t1, t2) -> t1 * t2));
    }

    public Long ops(Long t1, Long t2, Funca<Long, Long> funca) {
        return funca.op(t1, t2);
    }

    // 函数式接口使用
    @Test
    public void funTest() {
        Integer op = op(1000, (x) -> x * x);
        System.out.println(op);
    }

    public Integer op(Integer in, Fun<Integer> fun) {
        return fun.getValue(in);
    }

    // 6.Lambda表达式的参数列表的数据类型可以省略不写
    @Test
    public void paramTypeReturnLostTest() {
        Comparator<Integer> comparator = (Integer x, Integer y) -> Integer.compare(x, y);
        System.out.println(comparator.compare(1, 12));
    }

    // 5.只有一条语句，return和大括号都可以不写
    @Test
    public void paramReturnLostTest() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        System.out.println(comparator.compare(1, 12));
    }

    // 4.有一个或多个参数，有返回值，有多条处理语句
    @Test
    public void paramReturnTest() {
        Comparator<Integer> comparator = (x, y) -> {
            return Integer.compare(x, y);
        };
        System.out.println(comparator.compare(1, 12));
    }

    // 3.有一个参数，无返回值时可以省略括号
    @Test
    public void oneParamNoReturnLostTest() {
        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("Fv");
    }

    // 2.有一个参数，无返回值
    @Test
    public void oneParamNoReturnTest() {
        Consumer<String> consumer = (x) -> System.out.println(x);
        consumer.accept("Fv");
    }

    // 1.无参数，无返回值
    @Test
    public void noParamNoReturnTest() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World");
            }
        };
        runnable.run();
        System.out.println("---------Lambda实现--------");
        Runnable r = () -> System.out.println("Hello Lambda!");
        r.run();
    }

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
