package com.infrastructure.sync;

import com.infrastructure.model.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class CompletableFutureTest
{
    private List<Shop> shopList;
    private String[] productArray;

    public CompletableFutureTest(List<Shop> shopList, String[] productArray)
    {
        this.shopList = shopList;
        this.productArray = productArray;
    }

    @Test
    public void findPriceTest() throws Exception
    {
        Random random = new Random();
        int productIndex = random.nextInt(productArray.length);
        String product = productArray[productIndex];

        //1、顺序执行
        System.out.println("1 ================= " + product);
        long firstStart = System.currentTimeMillis();
        this.shopList.stream()
                .map(shop -> shop.getPrice(product))
                .forEach(priceOptional -> priceOptional.ifPresent(System.out::println));
        long firstEnd = System.currentTimeMillis();
        long firstCost = firstEnd - firstStart;
        System.out.println("1 ===================== ");
        System.out.println("cost: " + firstCost);

        //2、并发执行
        System.out.println("2 ================= " + product);
        long secondStart = System.currentTimeMillis();
        this.shopList.parallelStream()
                .map(shop -> shop.getPrice(product))
                .forEach(priceOptional -> priceOptional.ifPresent(System.out::println));
        long secondEnd = System.currentTimeMillis();
        long secondCost = secondEnd - secondStart;
        System.out.println("2 ===================== ");
        System.out.println("cost: " + secondCost);

        //3、使用CompletableFuture
        long thirdStart = System.currentTimeMillis();
        System.out.println("3 ================= " + product);
        this.shopList.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product)))
                .map(optionalCompletableFuture -> optionalCompletableFuture.join())
                .forEach(priceOptional -> priceOptional.ifPresent(System.out::println));
        long thirdEnd = System.currentTimeMillis();
        long thirdCost = thirdEnd - thirdStart;
        System.out.println("3 ===================== ");
        System.out.println("cost: " + thirdCost);

        //4、使用CompletableFuture，但是不直接获取结果
        long fourthStart = System.currentTimeMillis();
        System.out.println("4 ================= " + product);
        List<CompletableFuture<Optional<Double>>> fourthList = this.shopList.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product)))
                .collect(Collectors.toList());
        fourthList.stream()
                .map(optionalCompletableFuture -> optionalCompletableFuture.join())
                .forEach(priceOptional -> priceOptional.ifPresent(System.out::println));
        long fourthEnd = System.currentTimeMillis();
        long fourthCost = fourthEnd - fourthStart;
        System.out.println("4 ===================== ");
        System.out.println("cost: " + fourthCost);

        //5、使用CompletableFuture，异步输出
        CountDownLatch fifthCountDownLatch = new CountDownLatch(this.shopList.size());
        long fifthStart = System.currentTimeMillis();
        System.out.println("5 ================= " + product);
        this.shopList.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product)))
                .forEach(optionalCompletableFuture -> optionalCompletableFuture.thenApply(priceOptional -> {
                    priceOptional.ifPresent(System.out::println);
                    fifthCountDownLatch.countDown();
                    return priceOptional;
                }));
        long fifthEnd = System.currentTimeMillis();
        long fifthCost = fifthEnd - fifthStart;
        System.out.println("5 ===================== ");
        System.out.println("cost: " + fifthCost);

        fifthCountDownLatch.await();
    }

    @Test
    public void testLink() throws Exception
    {
        Random random = new Random();
        int productIndex = random.nextInt(productArray.length);
        String product = productArray[productIndex];

        //1、thenCompose
        CountDownLatch firstCountDownLatch = new CountDownLatch(this.shopList.size());
        this.shopList.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product)))
                .forEach(optionalCompletableFuture -> optionalCompletableFuture.thenCompose(priceOptional ->
                    CompletableFuture.supplyAsync(() -> {
                        priceOptional.ifPresent(System.out::println);
                        firstCountDownLatch.countDown();
                        return priceOptional;
                    })
                ));
        firstCountDownLatch.await();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data()
    {
        String[] product = new String[]{
                "iphone 6",
                "iphone 6s",
                "iphone 7",
                "iphone 7plus",
                "iphone 8",
                "iphone X"
        };
        String[] shopName = new String[]{
                "BestPrice",
                "LetsSaveBig",
                "MyFavoriteShop",
                "BuyItAll"
        };
        Random random = new Random();
        Function<Shop, Shop> registerProductFunction = shop -> {
            Arrays.stream(product)
                    .forEach(productName -> shop.registerProduct(productName, random.nextDouble() * 100));
            return shop;
        };
        List<Shop> shopList = Arrays.stream(shopName)
                .map(Shop::new)
                .map(registerProductFunction)
                .collect(Collectors.toList());
        Collection<Object[]> result = new ArrayList<>();
        result.add(new Object[]{shopList, product});
        return result;
    }
}
