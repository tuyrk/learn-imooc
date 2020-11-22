package com.tuyrk.stream;

import com.tuyrk.stream.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class FlatMapTests {
    private static final User[] arrayOfUsers = {
            User.builder().id(1L).username("zhangsan").name("张三").age(30).enabled(true).mobile("13000000001").roles(List.of("ROLE_ADMIN", "ROLE_USER")).build(),
            User.builder().id(2L).username("lisi").name("李四").age(32).enabled(false).mobile("13000000002").roles(List.of("ROLE_ADMIN")).build(),
            User.builder().id(3L).username("wangwu").name("王五").age(41).enabled(true).mobile("13000000003").roles(List.of("ROLE_USER")).build(),
    };

    private List<User> userList;

    @BeforeEach
    void setup() {
        userList = Arrays.asList(arrayOfUsers);
    }

    static class ThirdPartyApi {
        static Optional<Profile> findByUsername(String username) {
            return Arrays.stream(arrayOfUsers)
                    .filter(user -> !"zhangsan".equals(username) && user.getUsername().equals(username))
                    .findAny()
                    .map(user -> new Profile(user.getUsername(), "Hello, " + user.getName()));
        }
    }

    @Data
    @AllArgsConstructor
    static class Profile {
        private String username;
        private String greeting;
    }

    @Test
    public void givenUsersWithRoles_whenParentChild_withoutFlatMap() {
        List<List<String>> users = userList.stream()
                .map(User::getRoles)
                .peek(role -> log.debug("role: {}", role))
                .collect(Collectors.toList());
        log.debug("users: {}", users);
    }

    @Test
    public void givenUsersWithRoles_withFlatMap() {
        List<String> roleList = userList.stream()
                .flatMap(user -> user.getRoles().stream())
                .peek(role -> log.debug("role: {}", role))
                .collect(Collectors.toList());
        log.debug("roleList: {}", roleList);
    }

    @Test
    public void givenUsers_withOptional_thenWithStream() {
        List<Optional<Profile>> profiles = userList.stream()
                .map(user -> ThirdPartyApi.findByUsername(user.getUsername()))
                .peek(profile -> log.debug("profile: {}", profile))
                .collect(Collectors.toList());
        log.debug("profiles: {}", profiles);
    }

    @Test
    public void givenUsers_withOptional_thenFlatMapWithStream() {
        List<Profile> profiles = userList.stream()
                .map(user -> ThirdPartyApi.findByUsername(user.getUsername()))
                .flatMap(Optional::stream)
                // .flatMap(user -> ThirdPartyApi.findByUsername(user.getUsername()).stream())
                .peek(profile -> log.debug("profile: {}", profile))
                .collect(Collectors.toList());
        log.debug("profiles: {}", profiles);
    }

    @Test
    public void givenUsers_withOptional_thenDealElseWithStream() {

    }

    @Test
    public void givenUsersWithRoles_whenFlatMap_thenGroupByRole() {

    }

    @Test
    public void givenUsersWithRoles_whenFlatMap_thenGroupByRoleWithStream() {

    }
}
