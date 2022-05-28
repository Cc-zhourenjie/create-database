package com.cc.stream;

import com.cc.stream.pojo.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 学习Stream流操作
 *
 * @author zrj
 */
public class StreamLearn {
    static List<Person> personList = new ArrayList<Person>();

    static {
        personList.add(new Person("Tom", 8900, 25, "male", "New York"));
        personList.add(new Person("Jack", 7000, 28, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 23, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 27, "female", "New York"));
        personList.add(new Person("Owen", 9500, 30, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 25, "female", "New York"));
    }

    public static void main(String[] args) {

        //输出年龄为25岁的人员
        personList.stream().filter(i -> i.getAge() == 25).forEach(System.out::println);
        //获取第一个数据
        Optional<Person> first = personList.stream().filter(i -> i.getAge() == 25).findFirst();
        //匹配任意数据(并行流)
        Optional<Person> findAny = personList.parallelStream().filter(i -> i.getAge() == 25).findAny();
        //集合中是否有年龄等于30的人员
        boolean flag = personList.stream().anyMatch(i -> i.getAge() == 30);
        System.out.println("匹配第一个值：" + first.get());
        System.out.println("匹配任意数据：" + findAny.get());
        System.out.println("是否有年龄等于30的人员：" + flag);

        List<String> list = personList.stream().filter(i -> i.getAge() == 25).map(Person::getName).collect(Collectors.toList());
        System.out.println("年龄等于25岁的员工姓名：" + list);

        Optional<Person> max = personList.stream().max(Comparator.comparing(i -> i.getName().length()));
        System.out.println("名字最长的员工为：" + max.get().toString());

        Optional<Person> ageMax = personList.stream().max(Comparator.comparingInt(Person::getAge).reversed());
        System.out.println("年龄最大的员工为：" + ageMax.get().toString());


    }


//    public static void main(String[] args) {
//        //通过 java.util.Collection.stream() 方法用集合创建流
//        List<String> list = Arrays.asList("aa", "bb", "cc");
//        //创建一个顺序流
//        Stream<String> stream = list.stream();
//        //创建一个并行流
//        Stream<String> parallelStream = list.parallelStream();
//
//        //通过Steam静态方法创建流
//        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
//        //从下表0创建，每一位加3，一共创建4个数字
//        Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 3).limit(4);
//        //0,3,6,9
//        stream2.forEach(System.out::println);
//        //生成3个随机数
//        Stream<Double> stream3 = Stream.generate(Math::random).limit(3);
//        stream3.forEach(System.out::println);
//    }


}
