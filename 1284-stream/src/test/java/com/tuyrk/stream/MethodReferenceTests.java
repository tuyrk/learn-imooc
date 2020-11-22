package com.tuyrk.stream;

import com.tuyrk.stream.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Supplier;

@Slf4j
public class MethodReferenceTests {

    @Test
    public void givenStaticMethod_thenReferenceInFunction() {
        // Greeting greeting = (a, b) -> Player.sayHello(a, b);
        Greeting greeting = Player::sayHello;
        Assertions.assertEquals("hello:world", greeting.sayHello("hello", "world"));
    }

    interface Greeting {
        String sayHello(String s1, String s2);
    }

    static class Player {
        static String sayHello(String s1, String s2) {
            return s1 + ":" + s2;
        }
    }

    @Test
    public void givenStringIndex_thenReferenceInFunction() {
        // BiFunction<String, String, Integer> paramRef = (a, b) -> a.indexOf(b);
        BiFunction<String, String, Integer> paramRef = String::indexOf;
        Assertions.assertEquals(-1, paramRef.apply("hello", "world"));
    }

    @Test
    public void givenInstance_thenReferenceInFunction() {
        User user = User.builder().username("tuyrk").build();
        // Supplier<String> supplier = () -> user.getUsername();
        Supplier<String> supplier = user::getUsername;
        Assertions.assertEquals("tuyrk", supplier.get());
    }

    @Test
    public void givenNewInstance_thenReferenceConstructorInFunction() {
        // Supplier<User> supplier = () -> new User();
        Supplier<User> supplier = User::new;
        Assertions.assertNotNull(supplier.get());
    }
}
