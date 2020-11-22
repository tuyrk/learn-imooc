package com.tuyrk.stream;

import com.tuyrk.stream.domain.User;
import com.tuyrk.stream.domain.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class BasicOperatorTests {
    private static final User[] arrayOfUsers = {
            User.builder().id(1L).username("zhangsan").name("张三").enabled(true).mobile("13000000001").build(),
            User.builder().id(2L).username("lisi").name("李四").enabled(false).mobile("13000000002").build(),
            User.builder().id(3L).username("wangwu").name("王五").enabled(true).mobile("13100000000").build(),
    };

    private List<User> userList;

    @BeforeEach
    void setup() {
        userList = Arrays.asList(arrayOfUsers);
    }

    @Test
    public void givenUsers_whenForEach_thenChangeGender() {
        for (User user : arrayOfUsers) {
            user.setEnabled(true);
        }
        Assertions.assertTrue(userList.get(1).isEnabled());
    }

    @Test
    public void givenUsers_whenForEach_thenChangeGenderUsingStream() {
        userList.forEach(user -> user.setEnabled(true));
        Assertions.assertTrue(userList.get(1).isEnabled());
    }

    @Test
    public void givenUsers_whenForEachOrdered_thenPrintInfo() {
        List<User> toSort = new ArrayList<>();
        for (User user : userList) {
            toSort.add(user);
        }
        toSort.sort(Comparator.comparing(User::getUsername));
        for (User user : toSort) {
            log.debug("user: {}", user);
        }
    }

    @Test
    public void givenUsers_whenForEachOrdered_thenPrintInfoUsingStream() {
        userList.stream().sorted(Comparator.comparing(User::getUsername))
                .forEachOrdered(user -> log.debug("user: {}", user));
    }

    @Test
    public void givenUsers_whenMatchUsername_thenFindFirst() {
        User first = null;
        for (User user : arrayOfUsers) {
            if (user.getUsername().equals("lisi")) {
                first = user;
                break;
            }
        }
        Assertions.assertNotNull(first);
        Assertions.assertEquals("lisi", first.getUsername());
    }

    @Test
    public void givenUsers_whenMatchUsername_thenFindFirstUsingStream() {
        Optional<User> first = userList.stream()
                .filter(user -> user.getUsername().equals("lisi"))
                .findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertEquals("lisi", first.get().getUsername());
    }

    @Test
    public void givenUsers_whenMatchUsername_thenFindAnyUsingStream() {
        // findAny在并行任务中，不确保返回的是哪个，除了这点，它和findFirst没有区别
        Optional<User> first = userList.stream().findAny();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertEquals("zhangsan", first.get().getUsername());
    }

    @Test
    public void givenUsers_withAnyMatch_thenReturnTrue() {
        boolean existed = false;
        for (User user : userList) {
            if (user.getMobile().startsWith("130")) {
                existed = true;
                break;
            }
        }
        Assertions.assertTrue(existed);
    }

    @Test
    public void givenUsers_withAnyMatch_thenReturnTrueUsingStream() {
        boolean existed = userList.stream()
                .anyMatch(user -> user.getMobile().startsWith("130"));
        Assertions.assertTrue(existed);

        long count = userList.stream().filter(user -> user.getMobile().startsWith("130")).count();
        Assertions.assertEquals(2, count);

        Predicate<User> userMatches = user -> user.getMobile().startsWith("130");
        boolean existed2 = userList.stream().anyMatch(userMatches);
        Assertions.assertTrue(existed2);
    }

    @Test
    public void givenUsers_withNoneMatch_thenReturnTrue() {
        boolean notMatched = true;
        for (User user : userList) {
            if (user.getMobile().startsWith("130")) {
                notMatched = false;
                break;
            }
        }
        Assertions.assertFalse(notMatched);

        boolean matched = true;
        for (User user : userList) {
            if (user.getMobile().startsWith("132")) {
                matched = false;
                break;
            }
        }
        Assertions.assertTrue(matched);
    }

    @Test
    public void givenUsers_withNoneMatch_thenReturnTrueUsingStream() {
        boolean notMatched = userList.stream()
                .noneMatch(user -> user.getMobile().startsWith("130"));
        Assertions.assertFalse(notMatched);

        boolean matched = userList.stream()
                .noneMatch(user -> user.getMobile().startsWith("132"));
        Assertions.assertTrue(matched);
    }

    @Test
    public void givenUsers_withAllMatch_thenReturnTrueUsingStream() {
        boolean allMatched = userList.stream()
                .allMatch(user -> user.getMobile().startsWith("13"));
        Assertions.assertTrue(allMatched);
        boolean notMatched = userList.stream()
                .allMatch(user -> user.getMobile().equals("130"));
        Assertions.assertFalse(notMatched);
    }

    @Test
    public void givenUsers_withMap_thenTransformUsingStream() {
        List<String> userDTOS = userList.stream()
                .map(user -> UserDTO.builder()
                        .username(user.getUsername())
                        .name(user.getName())
                        .enabled(user.isEnabled() ? "激活" : "禁用")
                        .mobile(user.getMobile())
                        .build()
                )
                .map(UserDTO::getMobile)
                .collect(Collectors.toList());
        Assertions.assertEquals(userList.size(), userDTOS.size());
    }

    @Test
    public void givenUsers_whenFilterUsername_thenGetCount() {
        long count = 0;
        for (User user : userList) {
            if (user.getMobile().startsWith("130")) {
                count++;
            }
        }
        Assertions.assertEquals(2, count);
    }

    @Test
    public void givenUsers_whenFilterUsername_thenGetCountUsingStream() {
        long count = userList.stream()
                .filter(user -> user.getMobile().startsWith("130"))
                .count();
        Assertions.assertEquals(2, count);

        Predicate<User> userMobileStartWith130 = user -> user.getMobile().startsWith("130");
        long countWithPredicate = userList.stream()
                .filter(userMobileStartWith130)
                .count();
        Assertions.assertEquals(2, countWithPredicate);
    }

    @Test
    public void givenUsers_thenTestOtherTerminalOperatorsUsingStream() {
        Optional<User> userByMaxUserId = userList.stream().max(Comparator.comparing(User::getId));
        Assertions.assertTrue(userByMaxUserId.isPresent());
        Assertions.assertEquals(3L, userByMaxUserId.get().getId());

        Optional<User> userByMinUsername = userList.stream().min(Comparator.comparing(User::getUsername));
        Assertions.assertTrue(userByMinUsername.isPresent());
        Assertions.assertEquals("lisi", userByMinUsername.get().getUsername());
    }
}
