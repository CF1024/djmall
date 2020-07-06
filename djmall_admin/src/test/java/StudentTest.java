/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：StudentTest
 * 版权所有(C), 2020. 所有权利保留
 */

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengf
 * @date 2020/6/11 17:08
 * 测试
 */
public class StudentTest {

    public static void main(String[] args) {
        System.out.println("==========学生数据集合===========");
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1, "zs", "java", 78));
        studentList.add(new Student(2, "zs", "html", 63));
        studentList.add(new Student(3, "zs", "mysql", 70));
        studentList.add(new Student(4, "ls", "mysql", 66));
        studentList.add(new Student(5, "ls", "java", 32));
        studentList.add(new Student(6, "ww", "java", 99));
        studentList.add(new Student(7, "zl", "mysql", 67));
        System.out.println(studentList);

        //转换map
        System.out.println("==========转换Map<String, List<Student>>===========");
        Map<String, List<Student>> listMap = studentList.stream().collect(Collectors.groupingBy(Student::getStudentName));
        listMap.keySet().forEach(s -> System.out.println(s + listMap.get(s)));


        System.out.println("==========成绩大于60分的学生===========");
        List<Student> sixtyList = studentList.stream().filter(student -> student.getScore() > 60).collect(Collectors.toList());
        sixtyList.forEach(sixty -> {System.out.println(sixty.getStudentName()+"===="+sixty.getScore());});


        System.out.println("==========成绩大于70分的学生===========");
        List<Student> seventyList = studentList.stream().filter(student -> student.getScore() > 70).collect(Collectors.toList());
        Map<Integer, List<Student>> seventyListMap = seventyList.stream().collect(Collectors.groupingBy(Student::getScore));
        seventyListMap.keySet().forEach(sss -> System.out.println(seventyListMap.get(sss)));

        System.out.println("==========排序===========");
        List<Student> sortList = studentList.stream().sorted(Comparator.comparing(Student::getScore)).collect(Collectors.toList());
        sortList.forEach(sort -> {System.out.println(sort.getStudentName()+"===="+sort.getScore());});
    }

}
