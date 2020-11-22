package com.tuyrk.stream;

import com.tuyrk.stream.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SortedTests {
    private static final User[] arrayOfUsers = {
            User.builder().id(1L).username("zhangsan").name("张三").enabled(true).mobile("13000000001").build(),
            User.builder().id(2L).username("lisi").name("李四").enabled(false).mobile("13000000002").build(),
            User.builder().id(3L).username("wangwu").name("王五").enabled(true).mobile("13000000003").build(),
    };

    private List<User> userList;

    @BeforeEach
    void setup() {
        userList = Arrays.asList(arrayOfUsers);
    }

    @Test
    public void givenCollections_withoutStream_thenSort() {
        List<String> list = Arrays.asList("One", "Abc", "BCD");
        log.debug("未排序: {}", list);
        Assertions.assertEquals("One", list.get(0));

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        log.debug("排序后: {}", list);
        Assertions.assertEquals("Abc", list.get(0));
    }

    @Test
    public void givenCollections_withStream_thenSort() {
        List<String> list = Arrays.asList("One", "Abc", "BCD");
        List<String> sortedList = list.stream()
                .sorted()
                .collect(Collectors.toList());
        Assertions.assertEquals("Abc", sortedList.get(0));

        List<String> sortedListByFunc = list.stream()
                // .sorted((a, b) -> a.compareTo(b))
                // .sorted(Comparator.naturalOrder())
                .sorted(String::compareTo)
                .collect(Collectors.toList());
        Assertions.assertEquals("Abc", sortedListByFunc.get(0));

        List<String> descSortedList = list.stream()
                // .sorted((a, b) -> b.compareTo(a))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        Assertions.assertEquals("One", descSortedList.get(0));


        List<User> sortedUsers = userList.stream()
                // .sorted((a, b) -> a.getUsername().compareTo(b.getUsername()))
                .sorted(Comparator.comparing(User::getUsername))
                .sorted(Comparator.comparing(User::getUsername, String::compareTo))
                .collect(Collectors.toList());
        Assertions.assertEquals("lisi", sortedUsers.get(0).getUsername());

        Collator sortedByZhCN = Collator.getInstance(Locale.SIMPLIFIED_CHINESE);
        List<User> sortedUsersByChinese = userList.stream()
                .sorted(Comparator.comparing(
                        User::getName, sortedByZhCN
                ))
                .collect(Collectors.toList());
        Assertions.assertEquals("李四", sortedUsers.get(0).getName());
    }
}
