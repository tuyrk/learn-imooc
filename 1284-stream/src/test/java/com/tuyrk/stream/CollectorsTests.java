package com.tuyrk.stream;

import com.tuyrk.stream.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class CollectorsTests {
    private static final User[] arrayOfUsers = {
            User.builder().id(1L).username("zhangsan").name("张三").age(30).enabled(true).mobile("13000000001").build(),
            User.builder().id(2L).username("lisi").name("张三").age(32).enabled(false).mobile("13000000002").build(),
            User.builder().id(3L).username("wangwu").name("王五").age(41).enabled(true).mobile("13000000003").build(),
    };

    private List<User> userList;

    @BeforeEach
    void setup() {
        userList = Arrays.asList(arrayOfUsers);
    }

    @Test
    public void givenUsers_withToSet_thenSuccess() {
        Set<String> usernames = userList.stream()
                .map(User::getName)
                .collect(Collectors.toSet());
        Assertions.assertEquals(2, usernames.size());
    }

    @Test
    public void givenUsers_withToMap_thenSuccess() {
        Map<String, User> userMap = userList.stream()
                .collect(Collectors.toMap(
                        User::getUsername,
                        user -> user
                ));
        Assertions.assertTrue(userMap.containsKey("lisi"));

        System.out.println("====================");

        Map<String, User> duplicateMap = Stream.concat(userList.stream(), userList.stream())
                .peek(user -> log.debug("username, {}", user.getUsername()))
                .collect(Collectors.toMap(
                        User::getUsername,
                        user -> user,
                        (existing, replace) -> existing
                ));
        Assertions.assertEquals(3, duplicateMap.keySet().size());

        System.out.println("====================");

        TreeMap<String, User> sortedMap = Stream.concat(userList.stream(), userList.stream())
                .peek(user -> log.debug("username, {}", user.getUsername()))
                .collect(Collectors.toMap(
                        User::getUsername,
                        user -> user,
                        (existing, replace) -> existing,
                        TreeMap::new
                ));
        Assertions.assertEquals("lisi", sortedMap.keySet().stream().findFirst().get());
    }

    @Test
    public void givenUsers_withToCollection_thenSuccess() {
        Comparator<User> byAge = Comparator.comparing(User::getAge);
        TreeSet<User> users = userList.stream()
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(byAge)
                ));
        Assertions.assertEquals(30, users.stream().map(User::getAge).findFirst().orElse(-1));
    }

    @Test
    public void givenUsers_withSimpleSolarFunction_thenGetResult() {
        double avg = userList.stream().collect(Collectors.averagingDouble(User::getAge));
        Assertions.assertEquals((30 + 32 + 41) / 3.0, avg);

        // int sum = userList.stream().collect(Collectors.summingInt(User::getAge));
        int sum = userList.stream().mapToInt(User::getAge).sum();
        Assertions.assertEquals((30 + 32 + 41), sum);

        // DoubleSummaryStatistics stat = userList.stream().collect(Collectors.summarizingDouble(User::getAge));
        DoubleSummaryStatistics stat = userList.stream().mapToDouble(User::getAge).summaryStatistics();
        Assertions.assertEquals((30 + 32 + 41.0), stat.getSum());
        Assertions.assertEquals((30 + 32 + 41) / 3.0, stat.getAverage());
    }

    @Test
    public void givenUsers_whenGroupingByAge_thenGetStatWithStream() {
        Map<Integer, List<User>> map1 = userList.stream()
                .collect(Collectors.groupingBy(
                        user -> (int) Math.floor(user.getAge() / 10.0) * 10
                ));
        log.debug("result: {}", map1);

        System.out.println("===========================");

        Map<Integer, DoubleSummaryStatistics> map2 = userList.stream()
                .collect(Collectors.groupingBy(
                        user -> (int) Math.floor(user.getAge() / 10.0) * 10,
                        HashMap::new,
                        Collectors.summarizingDouble(User::getAge)
                ));
        log.debug("result: {}", map2);
        Assertions.assertEquals(2, map2.get(30).getCount());
        Assertions.assertEquals(32, map2.get(30).getMax());
        Assertions.assertEquals(30, map2.get(30).getMin());
        Assertions.assertEquals(31, map2.get(30).getAverage());
        Assertions.assertEquals(62, map2.get(30).getSum());
    }

    @Test
    public void givenUsers_whenGroupingByAge_thenGetListWithStream() {
        Map<Integer, List<UserDto>> result = userList.stream()
                .collect(Collectors.groupingBy(
                        user -> (int) Math.floor(user.getAge() / 10.0) * 10,
                        Collectors.mapping(
                                user -> new UserDto(user.getId(), user.getUsername() + ":" + user.getName()),
                                Collectors.toList()
                        )
                ));
        log.debug("result: {}", result);
    }

    @Data
    @AllArgsConstructor
    static class UserDto {
        private final Long id;
        private final String nickName;
    }

    @Test
    public void givenStrings_thenMappingAndFiltering_theChainThemTogether() {
        List<String> strings = List.of("bb", "ddd", "cc", "a");
        Map<Integer, TreeSet<String>> result = strings.stream()
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.mapping(
                                String::toUpperCase,
                                Collectors.filtering(
                                        s -> s.length() > 1,
                                        Collectors.toCollection(TreeSet::new)
                                )
                        )
                ));
        log.debug("result: {}", result);
    }

    @Test
    public void givenUsers_whenGroupingByAgeAndCollectingAndThen_thenGetCustomWithStream() {
        // collectingAndThen 其实就是在最后在做一个单一操作
        Map<Integer, UserStat> map = userList.stream()
                .collect(Collectors.groupingBy(
                        user -> (int) Math.floor(user.getAge() / 10.0) * 10,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    double average = list.stream().collect(Collectors.averagingDouble(User::getAge));
                                    return new UserStat(average, list);
                                }
                        )
                ));
        log.debug("map {}", map);
        Assertions.assertEquals(2, map.get(30).getUsers().size());
        Assertions.assertEquals(31.0, map.get(30).getAverage());
    }

    @Getter
    @AllArgsConstructor
    static class UserStat {
        private final double average;
        private final List<User> users;
    }

    @Test
    public void givenUsers_withJoining_thenGetString() {
        Map<String, String> requestParams = Map.of(
                "name", "张三",
                "username", "zhangsan",
                "email", "zhangsan@local.dev"
        );

        String url = requestParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .sorted()
                .collect(Collectors.joining("&", "http://local.dev/api?", ""));
        Assertions.assertEquals("http://local.dev/api?email=zhangsan@local.dev&name=张三&username=zhangsan", url);
    }

    @Test
    public void customCollector_whenUsingCollectorOf_then() {
        TreeMap<String, User> map = userList.stream().collect(Collector.of(
                TreeMap::new,
                (container, user) -> container.put(user.getUsername(), user),
                (result1, result2) -> {
                    result1.putAll(result2);
                    return result1;
                }
        ));

        Assertions.assertTrue(map.containsKey("lisi"));
    }
}
