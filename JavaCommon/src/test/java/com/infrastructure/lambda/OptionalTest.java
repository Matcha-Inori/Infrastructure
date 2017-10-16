package com.infrastructure.lambda;

import com.infrastructure.model.Car;
import com.infrastructure.model.Insurance;
import com.infrastructure.model.People;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.Properties;
import java.util.function.Predicate;

public class OptionalTest
{
    @Test
    public void testMap() throws Exception
    {
        People people = new People("Riven", 22, new Car());
        printInsuranceNameIfExist(people);

        People otherPeople = new People("Lily", 17, new Car(new Insurance("Matcha")));
        printInsuranceNameIfExist(otherPeople);
    }

    private void printInsuranceNameIfExist(People people)
    {
        Optional<People> peopleOptional = Optional.ofNullable(people);
        Optional<String> insuranceNameOptional = peopleOptional.flatMap(People::getCarOptional)
                .flatMap(Car::getInsuranceOptional)
                .map(Insurance::getName);
        insuranceNameOptional.ifPresentOrElse(System.out::println, () -> System.out.println("UnKnow"));
    }

    @Test
    public void testFilter() throws Exception
    {
        People riven = new People("Riven", 22, new Car(new Insurance("Matcha")));
        People lily = new People("Lily", 17);
        Optional<People> rivenOptional = Optional.ofNullable(riven);
        Optional<People> lilyOptional = Optional.ofNullable(lily);
        Predicate<People> hasCarPredicate = (People people) -> people.getCarOptional().isPresent();
        Predicate<People> namePredicate = (People people) -> "Lily".equals(people.getName());
        rivenOptional.filter(hasCarPredicate)
                .ifPresentOrElse(people -> System.out.println("1 -- true"), () -> System.out.println("1 -- false"));
        rivenOptional.filter(namePredicate)
                .ifPresentOrElse(people -> System.out.println("2 -- true"), () -> System.out.println("2 -- false"));

        lilyOptional.filter(hasCarPredicate)
                .ifPresentOrElse(people -> System.out.println("3 -- true"), () -> System.out.println("3 -- false"));
        lilyOptional.filter(namePredicate)
                .ifPresentOrElse(people -> System.out.println("4 -- true"), () -> System.out.println("4 -- false"));
    }

    @Test
    public void test() throws Exception
    {
        Properties properties = new Properties();
        properties.setProperty("a", "5");
        properties.setProperty("b", "true");
        properties.setProperty("c", "-3");
        properties.setProperty("d", "hh");
        properties.setProperty("f", "101");

        Assert.assertEquals(5, readDuration(properties, "a"));
        Assert.assertEquals(0, readDuration(properties, "b"));
        Assert.assertEquals(0, readDuration(properties, "c"));
        Assert.assertEquals(0, readDuration(properties, "d"));
        Assert.assertEquals(0, readDuration(properties, "e"));
        Assert.assertEquals(101, readDuration(properties, "f"));
    }

    private int readDuration(Properties properties, String propertyKey)
    {
        String propertyValue = properties.getProperty(propertyKey);
        return Optional.ofNullable(propertyValue)
                .map(value -> {
                    try {
                        return Integer.parseInt(value);
                    }
                    catch(NumberFormatException exception){
                        return 0;
                    }
                })
                .filter(value -> value > 0)
                .orElse(0);
    }
}
