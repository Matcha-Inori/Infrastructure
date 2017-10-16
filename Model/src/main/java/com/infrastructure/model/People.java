package com.infrastructure.model;

import java.util.Optional;

public class People
{
    private int age;
    private String name;

    private Car car;

    public People(String name, int age)
    {
        this(name, age, null);
    }

    public People(String name, int age, Car car)
    {
        this.name = name;
        this.age = age;
        this.car = car;
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

    public Car getCar()
    {
        return car;
    }

    public void setCar(Car car)
    {
        this.car = car;
    }

    public Optional<Car> getCarOptional()
    {
        return Optional.ofNullable(this.car);
    }
}
