package com.bwie.day.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 1. 类的用途
 * 2. @author admin
 * 3. @date 2017/8/30 19:29
 */
@Table(name = "Student",onCreated = "")
public class Student {
    @Column(name="id",property = "NOT NULL" ,isId =true,autoGen = true)
    private int id;
    @Column(name="name")
    private String name;
    //无参构造器必须写
    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
