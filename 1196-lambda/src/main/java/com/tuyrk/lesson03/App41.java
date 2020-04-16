package com.tuyrk.lesson03;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 4-1 方法引用
 * 1. 静态方法引用的使用
 * 类型名称.方法名称() --> 类型名称::静态方法名称
 * 2. 实例方法引用的使用
 * 创建类型对应的一个对象 --> 对象应用::实例方法名称
 *
 * @author tuyrk
 */
public class App41 {
    public static void main(String[] args) {
        // 存储Person对象的列表
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("tom", "男", 16));
        personList.add(new Person("jerry", "女", 15));
        personList.add(new Person("shuke", "男", 30));
        personList.add(new Person("beita", "女", 26));
        personList.add(new Person("damu", "男", 32));

        // ===对年龄进行排序===
        // 1. 匿名内部类实现方式
        /*Collections.sort(personList, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() - o2.getAge();
            }
        });
        System.out.println(personList);*/
        // 2. Lambda表达式实现方式
        /*Collections.sort(personList, (o1, o2) -> {
            return o1.getAge() - o2.getAge();
        });
        System.out.println(personList);*/
        // 3. 静态方法引用
        /*Collections.sort(personList, Person::compareByAge);
        System.out.println(personList);*/

        // 4. 实例方法引用
        Person p = new Person();
        Collections.sort(personList, p::compareByName);
        System.out.println("tom".hashCode());
        System.out.println("jerry".hashCode());
        System.out.println(personList);

        // 5. 构造方法引用：绑定函数式接口
        IPerson ip = Person::new;
        Person person = ip.initPerson("jerry", "男", 22);
        System.out.println(person);

    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Person {
    private String name;// 姓名
    private String gender;// 性别
    private int age;//年龄

    // 静态方法
    public static int compareByAge(Person p1, Person p2) {
        return p1.getAge() - p2.getAge();
    }

    // 实例方法
    public int compareByName(Person p1, Person p2) {
        return p1.getName().hashCode() - p2.getName().hashCode();
    }
}

@FunctionalInterface
interface IPerson {
    // 抽象方法：通过指定类型的构造方法初始化对象数据
    Person initPerson(String name, String gender, int age);
}
