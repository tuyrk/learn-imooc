package com.tuyrk.stream;

import com.tuyrk.stream.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class CreateStreamTests {

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
    public void givenUsers_createStreamWithArray() {
        List<User> list = Arrays.stream(arrayOfUsers)
                .peek(user -> log.debug("user: {}", user))
                .collect(Collectors.toList());
        Assertions.assertEquals(userList.size(), list.size());
    }

    @Test
    public void givenUsers_createStreamWithList() {
        List<User> list = userList.stream()
                .peek(user -> log.debug("user: {}", user))
                .collect(Collectors.toList());
        Assertions.assertEquals(userList.size(), list.size());
    }

    @Test
    public void givenUsers_createStreamWithStreamOf() {
        List<User> list = Stream.of(arrayOfUsers[0], arrayOfUsers[1], arrayOfUsers[2])
                .peek(user -> log.debug("user: {}", user))
                .collect(Collectors.toList());
        Assertions.assertEquals(arrayOfUsers.length, list.size());
    }

    @Test
    public void givenUsers_createStreamWithStreamIterate() {
        List<Integer> list = Stream.iterate(0, n -> n + 1)
                .limit(10)
                .peek(n -> log.debug("the number is {}", n))
                .collect(Collectors.toList());
        Assertions.assertEquals(10, list.size());
    }

    @Test
    public void givenUsers_createStreamWithStreamGenerate() {
        List<Double> list = Stream.generate(Math::random)
                .limit(10)
                .peek(n -> log.debug("the number is {}", n))
                .collect(Collectors.toList());
        Assertions.assertEquals(10, list.size());
    }

    @Test
    public void givenUsers_createStreamWithStreamSplitIterator() {
        Iterator<User> iterator = userList.iterator();
        Spliterator<User> spliterator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.NONNULL);
        Stream<User> userStream = StreamSupport.stream(spliterator, false);
        List<User> list = userStream
                .peek(user -> log.debug("user: {}", user))
                .collect(Collectors.toList());
        Assertions.assertEquals(userList.size(), list.size());
    }

    @Test
    public void givenIntegerRange_createStreamWithIntStream() {
        List<Integer> list = IntStream.rangeClosed(0, 5)
                .boxed()
                .peek(i -> log.debug("the number is {}", i))
                .collect(Collectors.toList());
        assertEquals(6, list.size());
    }

    @Test
    public void givenUsers_createStreamWithStreamBuilder() {
        Stream.Builder<User> userStreamBuilder = Stream.builder();
        List<User> list = userStreamBuilder
                .add(arrayOfUsers[0])
                .add(arrayOfUsers[1])
                .add(arrayOfUsers[2])
                .build()
                .skip(1) // 跳过1个元素
                .peek(user -> log.debug("user: {}", user))
                .collect(Collectors.toList());
        Assertions.assertEquals(arrayOfUsers.length - 1, list.size());
    }

    @Test
    public void givenSentence_createStreamWithNewAPIs() {

    }
}
