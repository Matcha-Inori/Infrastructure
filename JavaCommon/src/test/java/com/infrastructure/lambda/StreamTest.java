package com.infrastructure.lambda;

import com.infrastructure.model.Dish;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class StreamTest
{
    private List<Dish> menu;

    public StreamTest(List<Dish> menu)
    {
        this.menu = menu;
    }

    @Test
    public void test() throws Exception
    {
        List<Dish> menu = copy(this.menu);
        List<String> menuNameList = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());
        menuNameList.stream().forEach(System.out::println);
    }

    @Test
    public void testFlatMap() throws Exception
    {
        List<Integer> first = Arrays.asList(1, 2, 3);
        List<Integer> second = Arrays.asList(3, 4);

        List<int[]> result = first.stream()
                .flatMap(firstNum -> second.stream().map(secondNum -> new int[]{firstNum, secondNum}))
                .collect(Collectors.toList());
        result.stream().map(Arrays::toString).forEach(System.out::println);

        System.out.println("==========");

//        result = first.stream()
//                .flatMap(firstNum -> second.stream().map(secondNum -> new int[]{firstNum, secondNum}))
//                .filter(array -> Arrays.stream(array).reduce((sum, num) -> sum + num).getAsInt() % 3 == 0)
//                .collect(Collectors.toList());
        result = first.stream()
                .flatMap(firstNum -> second.stream().filter(secondNum -> (firstNum + secondNum) % 3 == 0).map(secondNum -> new int[]{firstNum, secondNum}))
                .collect(Collectors.toList());
        result.stream().map(Arrays::toString).forEach(System.out::println);
    }

    private List<Dish> copy(List<Dish> src)
    {
        List<Dish> target = new ArrayList<>(src.size());
        src.stream().forEach(target::add);
        return target;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data()
    {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );
        List<Object[]> params = new ArrayList<>();
        params.add(new Object[]{menu});
        return params;
    }
}
