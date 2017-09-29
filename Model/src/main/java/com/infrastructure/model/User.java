package com.infrastructure.model;

public class User
{
    private long userId;
    private int age;
    private String name;

    public User()
    {
    }

    public User(long userId, int age, String name)
    {
        this.userId = userId;
        this.age = age;
        this.name = name;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "userId=" + userId +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
