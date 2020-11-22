package com.tuyrk.stream;

import com.tuyrk.stream.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class OptionalStreamTests {
    private static final User[] arrayOfUsers = {
            User.builder().id(1L).username("zhangsan").name("张三").enabled(true).mobile("13000000001").build(),
            User.builder().id(2L).username("lisi").name("李四").enabled(false).mobile("13000000002").build(),
            User.builder().id(3L).username("wangwu").name("王五").enabled(true).mobile("13100000000").build(),
    };

    private MockRepo repo;

    @BeforeEach
    void setup() {
        repo = new MockRepo();
    }

    static class MockRepo {
        Optional<User> findByUsername(String username) {
            return Arrays.stream(arrayOfUsers)
                    .filter(user -> user.getUsername().equals(username))
                    .findAny();
        }
    }

    @Test
    public void givenUsers_whenQueryOptional_thenCheckPresent() {
        boolean existed = repo.findByUsername("zhangsan")
                .map(User::getUsername)
                .isPresent();
        Assertions.assertTrue(existed);
    }

    @Test
    public void givenUsers_whenQueryEmpty_thenOrElseThrow() {
        repo.findByUsername("zhangsan")
                .map(User::getUsername)
                .orElseThrow();
        repo.findByUsername("zhaoliu")
                .map(User::getUsername)
                .orElseThrow(NullPointerException::new);
    }

    @Test
    public void givenUsers_whenQueryEmpty_thenOrElse() {
        String username = repo.findByUsername("zhaoliu")
                .map(User::getUsername)
                .orElse("未知用户");
        Assertions.assertNotNull(username);
        Assertions.assertEquals("未知用户", username);
    }

    @Test
    public void givenUsers_whenQueryEmpty_thenOrElseGet() {
        String username = repo.findByUsername("zhaoliu")
                .map(User::getUsername)
                .orElseGet(() -> "anonymous");
        Assertions.assertNotNull(username);
        Assertions.assertEquals("anonymous", username);
    }

    @Test
    public void givenUsers_whenQueryEmpty_thenOr() {
        Optional<String> usernameOptional = repo.findByUsername("zhaoliu")
                .map(User::getUsername)
                .or(() -> Optional.of("notExisted"));
        Assertions.assertTrue(usernameOptional.isPresent());
        Assertions.assertEquals("notExisted", usernameOptional.get());

    }

    @Test
    public void givenUsers_whenQuerying_thenIfPresent() {
        repo.findByUsername("zhangsan")
                .map(User::getUsername)
                .ifPresent(username -> {
                    log.debug("username: {}", username);
                    Assertions.assertEquals("zhangsan", username);
                });
    }

    @Test
    public void givenUsers_whenQuerying_thenIfPresentOrElse() {
        repo.findByUsername("zhangsan")
                .map(User::getUsername)
                .ifPresentOrElse(username -> {
                    log.debug("username: {}", username);
                    Assertions.assertEquals("zhangsan", username);
                }, () -> {
                    log.debug("cannot reach else block");
                });

        repo.findByUsername("zhaoliu")
                .map(User::getUsername)
                .ifPresentOrElse(username -> {
                    log.debug("cannot reach this block");
                }, () -> {
                    Assertions.assertTrue(true);
                });
    }
}
