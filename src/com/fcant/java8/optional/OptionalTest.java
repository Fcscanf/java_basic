package com.fcant.java8.optional;

import com.fcant.java8.lambda.bean.Employee;
import com.fcant.java8.optional.bean.Godness;
import com.fcant.java8.optional.bean.Man;
import com.fcant.java8.optional.bean.NewMan;
import org.junit.Test;

import java.util.Optional;

/**
 * OptionalTest
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 上午 10:29:10 2020/2/23/0023
 */
public class OptionalTest {

    /**
     * Optional容器类常用方法
     * 1.Optional.of(T t) 创建一个Optional实例
     * 2.Optional.empty() 创建一个空的Optional实例
     * 3.Optional.ofNullable(T t) 若t不为null，创建Optional实例，否则创建空实例
     * 4.isPresent() 判断是否包含值
     * 5.orElse(T t) 如果调用对象包含值，返回该值，否则返回t
     * 6.orElseGet(Supplier s) 如果调用对象包含值，返回该值，否则返回s获取的值
     * 7.map(Function f) 如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
     * 8.flatMap(Function mapper) 与map类似，要求返回值必须是Optional
     *
     * @author Fcant 上午 10:30:11 2020/2/23/0023
     */
    @Test
    public void optionalTest() {
        Optional<Employee> employeeOptional = Optional.of(new Employee());
        Employee employee = employeeOptional.get();
        System.out.println(employee);

        // 构建null的Optional容器

        Optional<Employee> empty = Optional.empty();

        Optional<Employee> objectOptional = Optional.ofNullable(new Employee("Fcant", 18, 19999.0, Employee.Status.BUSY));
        if (objectOptional.isPresent()) {
            System.out.println(objectOptional.get());
        } else {
            // 如果为空，设置默认值
            Employee fcant = objectOptional.orElse(new Employee("Fcant", 18, 19999.0, Employee.Status.BUSY));
            System.out.println(fcant);
        }

        Employee orElseGetEmployee = objectOptional.orElseGet(() -> new Employee());
        System.out.println(orElseGetEmployee);

        Optional<String> empName = objectOptional.map(e -> e.getName());
        System.out.println(empName.get());

        // flatMap要求必须返回是Optional
        Optional<String> optionalS = objectOptional.flatMap(e -> Optional.of(e.getName()));
        System.out.println(optionalS.get());
    }

    // 练习案例
    // 需求：获取一个男人心中女神的名字
    public String getGodnessName(Man man) {
        if (man != null) {
            Godness godness = man.getGodness();
            if (godness != null) {
                return godness.getName();
            }
        }
        return "苍井空";
    }

    public String getGodnessName(Optional<NewMan> newMan) {
        return newMan.orElse(new NewMan())
                .getGodness()
                .orElse(new Godness("苍井空"))
                .getName();
    }

    @Test
    public void Test() {
        Optional<NewMan> newMan = Optional.empty();
        String godnessName = getGodnessName(newMan);
        System.out.println(godnessName);
    }
}
