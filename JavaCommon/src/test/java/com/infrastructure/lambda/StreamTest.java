package com.infrastructure.lambda;

import com.infrastructure.model.Dish;
import com.infrastructure.model.Trader;
import com.infrastructure.model.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;
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

    @Test
    public void testFind() throws Exception
    {
        List<Dish> menu = copy(this.menu);
        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(System.out::println);

        menu.parallelStream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(System.out::println);
    }

    @Test
    public void testQuery() throws Exception
    {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //1、找出2011年发生的所有交易，并按交易额排序(从低到高)
        List<Transaction> resultOne = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        resultOne.stream().forEach(System.out::println);

        System.out.println("==========");

        //2、交易员都在哪些不同的城市工作过？
        List<String> resultTwo = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .collect(Collectors.toList());
        resultTwo.stream().reduce((left, right) -> left + ", " + right).ifPresent(System.out::println);
//        String resultTwoStr = resultTwo.stream().collect(Collectors.joining());
//        System.out.println(resultTwoStr);

        System.out.println("==========");

        //3、查找所有来自于剑桥的交易员，并按姓名排序
        List<Trader> resultThird = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> "Cambridge".equals(trader.getCity()))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        resultThird.stream().forEach(System.out::println);

        System.out.println("==========");

        //4、返回所有交易员的姓名字符串，按照字母顺序排序
        List<String> resultFour = transactions.stream()
                .map(Transaction::getTrader)
                .distinct()
                .map(Trader::getName)
                .sorted(Comparator.comparing(name -> name.charAt(0)))
                .collect(Collectors.toList());
        resultFour.stream().forEach(System.out::println);

        System.out.println("==========");

        //5、有没有交易员是在米兰工作的
        boolean resultFive = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .anyMatch(city -> "Milan".equals(city));
        System.out.println(resultFive);

        System.out.println("==========");

        //6、打印生活在剑桥的交易员的所有交易额
        transactions.stream()
                .filter(transaction -> {
                    Trader trader = transaction.getTrader();
                    String city = null == trader ? null : trader.getCity();
                    return "Cambridge".equals(city);
                })
                .map(Transaction::getValue)
                .forEach(System.out::println);

        System.out.println("==========");

        //7、所有交易额中，最高的交易额是多少
        transactions.stream()
                .map(Transaction::getValue)
                .max(Integer::compare)
                .ifPresent(System.out::println);

        System.out.println("==========");

        //8、找到交易额最小的交易
        transactions.stream()
                .min(Comparator.comparing(Transaction::getValue))
                .ifPresent(System.out::println);
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
