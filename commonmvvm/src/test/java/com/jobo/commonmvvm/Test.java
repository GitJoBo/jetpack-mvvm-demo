package com.jobo.commonmvvm;

import java.util.ArrayList;
import java.util.Objects;

public class Test {
    //    @org.junit.Test
//    public static void main(String[] args) {
//        Student zs = new Student("张三");
//        Student zs2 = new Student("张三");
//        System.out.println("zs.equals(zs)=" + zs.equals(zs));
//        System.out.println("zs.equals(zs2)=" + zs.equals(zs2));
//    }
    volatile int aa = 0;


    @org.junit.Test
    public void test() {
        demo2();
//        demo();
    }

    private void demo2() {
        Student zs = new Student("张三");
        Student zs2 = new Student("张三");
        Student2 zs3 = new Student2("张三");
        System.out.println("zs.equals(zs)=" + zs.equals(zs));
        System.out.println("zs == (zs2)=" + (zs == zs2));
        System.out.println("zs.equals(zs2)=" + zs.equals(zs2));
        System.out.println("zs3.equals(zs2)=" + zs3.equals(zs2));
    }

    /**
     * 按值传递 引用传递
     */
    private void demo() {
        //按值传递 输出0 0
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(0);
        System.out.println(integers.get(0));
        Integer integer = integers.get(0);
        integer = 1;
        System.out.println(integers.get(0));
        //引用传递 输出3333 444
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("3333"));
        System.out.println(students.get(0).name);
        Student student = students.get(0);
        student.name = "444";
        System.out.println(students.get(0).name);
    }


    static class Student {
        String name;
        int age;
        String job;

        public Student(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return Objects.equals(name, student.name);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
//            return Objects.hash(name);
        }
    }

    static class Student2 {
        String name;
        int age;
        String job;

        public Student2(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return Objects.equals(name, student.name);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
//            return Objects.hash(name);
        }
    }
}

