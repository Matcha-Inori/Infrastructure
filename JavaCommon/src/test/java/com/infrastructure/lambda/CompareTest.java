package com.infrastructure.lambda;

import com.infrastructure.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CompareTest
{
    @Test
    public void test() throws Exception
    {
        List<User> userList = createList();

        Consumer<User> userPrinter = System.out::println;

        userList.stream().forEach(userPrinter);
        System.out.println("===============");
        System.out.println("===============");

        List<User> copyList1 = copy(userList);
        Collections.sort(copyList1, Comparator.comparing(User::getAge));
        copyList1.stream().forEach(userPrinter);
        System.out.println("===============");

        List<User> copyList2 = copy(userList);
        Collections.sort(
                copyList2,
                Comparator.comparing(User::getAge)
                        .thenComparing(Comparator.comparing(User::getUserId))
        );
        copyList2.stream().forEach(userPrinter);
        System.out.println("===============");
    }

    @Test
    public void check() throws Exception
    {
        List<User> userList = createList();
        Predicate<User> userAgePredicate = user -> user.getAge() >= 12;
        Predicate<User> userIdPredicate = user -> user.getUserId() < 5L;
        Predicate<User> finallyUserPredicate = userAgePredicate.and(userIdPredicate);
        userList.stream().filter(finallyUserPredicate).forEach(System.out::println);
    }

    private List<User> createList()
    {
        List<User> userList = new ArrayList<>();
        userList.add(new User(6L, 15, "F"));
        userList.add(new User(1L, 10, "A"));
        userList.add(new User(5L, 14, "E"));
        userList.add(new User(6L, 12, "G"));
        userList.add(new User(3L, 12, "C"));
        userList.add(new User(4L, 13, "D"));
        userList.add(new User(2L, 11,"B"));
        return userList;
    }

    private List<User> copy(List<User> src)
    {
        List<User> target = new ArrayList<>(src.size());
        src.stream().forEach(target::add);
        return target;
    }
}
