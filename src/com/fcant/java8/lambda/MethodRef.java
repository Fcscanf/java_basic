package com.fcant.java8.lambda;

import com.fcant.java8.lambda.bean.Employee;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * MethodRef
 * <p>
 * encoding:UTF-8
 *
 * A.方法引用：若Lambda体中的内容有方法已经实现了，我们可以使用“方法引用”
 *  （可以理解为方法引用是Lambda表达式的另外一种表现形式）
 *
 *  主要有三种语法格式：
 *
 *  对象::实例方法名
 *
 *  类::静态方法名
 *
 *  类::实例方法名
 *
 *  注意：
 *  一、Lambda体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致
 *  二、若Lambda参数列表中的第一参数是实例方法的调用者，而第二个参数是实例方法的参数时，可以使用类名::方法名
 *
 *  B.构造器引用
 *  语法格式：
 *      ClassName::new;
 *  注意：需要调用的构造器的参数列表与函数式接口中抽象方法的参数列表保持一致。
 *
 *  C.数组引用
 *  语法格式：
 *      Type[]:new;
 *
 * @author Fcant 下午 22:11:03 2020/2/19/0019
 */
public class MethodRef {

    // 数组引用
    @Test
    public void typeTest() {
        Function<Integer, String[]> function = (x) -> new String[x];
        String[] strings = function.apply(10);
        System.out.println(strings.length);

        Function<Integer, String[]> integerFunction = String[]::new;
        String[] s = integerFunction.apply(20);
        System.out.println(s.length);
    }

    // 构造器引用
    @Test
    public void constructTest() {
        Supplier<Employee> supplier = () -> new Employee();
        Supplier<Employee> employeeSupplier = Employee::new;
        System.out.println(employeeSupplier.get());

        Function<String, Employee> function = (x) -> new Employee(x);
        Function<String, Employee> employeeStringFunction = Employee::new;
        System.out.println(employeeStringFunction.apply("Fcant"));
    }

    // 类::实例方法名

    @Test
    public void Test() {
        BiPredicate<String, String> biPredicate = (x, y) -> x.equals(y);
        // 实例方法的第一个参数作为调用者，第二个参数作为调用者的参数，就可以使用类::实例方法名进行调用
        BiPredicate<String, String> stringBiPredicate = String::equals;
    }

    // 类::静态方法名

    @Test
    public void methodStaticTest() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        Comparator<Integer> integerComparator = Integer::compare;
    }

    // 对象::实例方法名

    @Test
    public void methodTest() {
        Consumer<String> consumer = (x) -> System.out.println(x);
        consumer.accept("一般的Lambda实现");
        Consumer<String> stringConsumer = System.out::println;
        stringConsumer.accept("使用同类型的方法进行引用实现");

        // getName()是employee对象的实例方法
        Employee employee = new Employee("Fcant", 16, 1600);
        Supplier<String> stringSupplier = employee::getName;
        System.out.println(stringSupplier.get());
    }

}
