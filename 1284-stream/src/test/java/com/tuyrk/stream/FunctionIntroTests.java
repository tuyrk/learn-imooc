package com.tuyrk.stream;

import com.tuyrk.stream.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public class FunctionIntroTests {
    @Test
    public void givenFunctionalInterface_thenFunctionAsArgumentsOrReturnValue() {
        HigherOrderFunctionClass higherOrderFunctionClass = new HigherOrderFunctionClass();
        IFactory<User> factory = higherOrderFunctionClass.createFactory(
                () -> User.builder().id(100L).name("tuyrk").build(),
                user -> {
                    log.debug("用户信息：{}", user);
                    user.setMobile("18382471393");
                }
        );

        User user = factory.create();
        Assertions.assertEquals("tuyrk", user.getName());
        Assertions.assertEquals(100L, user.getId());
        Assertions.assertEquals("18382471393", user.getMobile());
    }

    interface IFactory<T> {
        T create();
    }

    interface IProducer<T> {
        T produce();
    }

    interface IConfigurator<T> {
        void configure(T t);
    }

    static class HigherOrderFunctionClass {
        public <T> IFactory<T> createFactory(IProducer<T> producer, IConfigurator<T> configurator) {
            return () -> {
                T instance = producer.produce();
                configurator.configure(instance);
                return instance;
            };
        }
    }

    class TestProducer implements IProducer {
        @Override
        public Object produce() {
            return null;
        }
    }

    @Test
    public void givenTwoFunctions_thenComposeThemInNewFunction() {

    }

    @Test
    public void givenTwoPredicates_thenComposeThemUsingAnd() {
        Predicate<String> startsWithA = (text) -> text.startsWith("A");
        Predicate<String> endsWithX = (text) -> text.endsWith("X");
        Predicate<String> startsWithAAndEndsWithX = startsWithA.and(endsWithX);
        Assertions.assertFalse(startsWithAAndEndsWithX.test("ABCD"));
        Assertions.assertTrue(startsWithAAndEndsWithX.test("ABCDX"));
    }

    @Test
    public void givenTwoPredicates_thenComposeThemUsingOr() {
        Predicate<String> startsWithA = (text) -> text.startsWith("A");
        Predicate<String> endsWithX = (text) -> text.endsWith("X");
        Predicate<String> startsWithAAndEndsWithX = startsWithA.or(endsWithX);
        Assertions.assertTrue(startsWithAAndEndsWithX.test("ABCD"));
        Assertions.assertTrue(startsWithAAndEndsWithX.test("BCDX"));
        Assertions.assertTrue(startsWithAAndEndsWithX.test("ABCDX"));
        Assertions.assertFalse(startsWithAAndEndsWithX.test("BCD"));
    }

    @Test
    public void givenTwoPredicates_thenComposeThemUsingCompose() {
        Function<Integer, Integer> squareOp = (value) -> value * value;
        Function<Integer, Integer> doubleOp = (value) -> 2 * value;
        Function<Integer, Integer> doubleThenSquare = squareOp.compose(doubleOp);
        Assertions.assertEquals(36, doubleThenSquare.apply(3));
    }

    @Test
    public void givenTwoPredicates_thenComposeThemUsingAndThen() {
        Function<Integer, Integer> squareOp = (value) -> value * value;
        Function<Integer, Integer> doubleOp = (value) -> 2 * value;
        Function<Integer, Integer> squareThenDouble = squareOp.andThen(doubleOp);
        Assertions.assertEquals(18, squareThenDouble.apply(3));
    }
}
