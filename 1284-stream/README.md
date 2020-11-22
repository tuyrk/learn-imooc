# JAVA 函数式编程

> https://www.imooc.com/learn/1284
>
> 简介：本课程以 Java 11 为编译环境，讲解了 Java 对函数式编程支持，以及用实战小例子演示如何使用函数式简洁优雅的直击问题核心逻辑。

## 第1章 课程介绍

> 本章主要针对环境配置、函数式编程的概念及特点等内容进行了讲解，帮助同学们对函数式编程有初步的认识。

### 1-1 课程介绍和环境配置

课程介绍：开发环境和数据对象的介绍、函数式编程介绍、函数式编程的特点

流的常见操作符和应用场景：什么是流、基础操作符、Optional流、收集器之集合对象、分组统计和聚合函数、收集阶段的操作符、排序

高阶操作符：flatMap、reduce

编译和运行环境：Java11 SDK、IDEA IDE、Maven

### 1-2 函数式编程介绍

#### 什么是函数式编程

函数式编程是一种编程范式，它将计算视为函数的运算，并避免变化状态和可变数据。它是一种声明式编程范式，也就是说，编程是用表达式或声明而不是语句来完成的。

```
Lambda表达式：(a, b) -> a + b
```

比起指令式编程，函数式编程更加强调程序执行的结果而非执行的过程，倡导利用若干简单的执行单元让计算结果不断渐进，逐层推导复杂的运算，而不是设计一个复杂的执行过程。

```java
userList.stream()
  .skip(1)
  .peek(user -> log.debug("user: {}", user.getUsername()))
  .collect(Collectors.toList())
```

#### 函数式带来哪些好处

例1：创建匿名函数

```java
button.addActionListener(new ActionListener() {
  @Override
  public void actionPerformed(ActionEvent e) {
    doSomethingWith(e);
  }
});
```

```java
button.addActionListener(e -> doSomethingWith(e));
```

例2：倒序排序

```java
Collections.sort(list, new Comparator<Integer>() {
  @Override
  public int compare(final Integer o1, final Integer o2) {
    return o2.compareTo(o1);
  }
});
```

```java
Collections.sort(list, (o1, o2) -> o2.compareTo(o1));
Collections.sort(list, Comparator.reverseOrder());
```

例3：给出符合以下条件的作者列表：页数最多、前三条、排除书名为葵花宝典

```java
List<Book> booksFiltered = new ArrayList<>();
for (Book book : books) {
  if (!"葵花宝典".equals(book.getTitle())) {
    booksFiltered.add(book);
  }
}
booksFiltered.sort(new Comparator<Book>() {
  @Override
  public int compare(Book o1, Book o2) {
    return o2.getPages().compareTo(o1.getPages());
  }
});
for (int i = 0; i < 3; i++) {
  System.out.println(booksFiltered.get(i).getAuthor());
}
```

```java
books.stream()
  .filter(b -> !"葵花宝典".equals(b.getTitle()))
  .sorted((b1, b2) -> b2.getPages().compareTo(b1.getPages()))
  .limit(3) // 取前三
  .map(Book::getAuthor)
  .distinct() // 去重
  .forEach(System.out::println);
```

### 1-3 函数式编程的特点

#### 纯函数

函数的执行没有副作用。

返回值仅依赖输入参数

#### 高阶函数

函数的参数可以是一个或多个函数

函数的返回值也可以是一个函数

```java
HigherOrderFunctionClass higherOrderFunctionClass = new HigherOrderFunctionClass();
IFactory<User> factory = higherOrderFunctionClass.createFactory(
  () -> User.builder().id(100L).name("tuyrk").build(),
  user -> {
    log.debug("用户信息：{}", user);
    user.setMobile("18382471393");
  }
);
```

#### 箭头左边是参数列表，右边是函数体

#### 方法引用 class::method

- 静态方法引用

  ```java
  Combine combine = (a, b) -> User.combine(a, b);
  Combine combine = User::combine
  ```

- 参数方法引用

  ```java
  BiFunction<String, String, Integer> paramRef = (a, b) -> a.indexOf(b);
  BiFunction<String, String, Integer> paramRef = String::indexOf;
  ```

- 实例方法引用

  ```java
  User user = User.builder().username("tuyrk").build();
  Supplier<String> supplier = () -> user.getUsername();
  Supplier<String> supplier = user::getUsername;
  ```

- 构造器引用

  ```java
  Supplier<User> supplier = () -> new User();
  Supplier<User> supplier = User::new;
  ```

#### Java函数式接口

有且仅有一个未实现的静态方法的接口叫做“函数式接口”

#### 内建的函数式接口

- Function：表示一个函数（方法），它接收一个单一的参数并返回一个单一的值

  ```java
  Function<Long, Long> some = (value) -> value + 3;
  Long resultLambda = some.apply(8L);
  ```

- Predicate：表示一个简单的函数，它只取一个值作为参数并返回真或者假

  ```java
  Predicate<Object> predicate = (value) -> value != null;
  ```

- UnaryOperator：代表一个操作，它接收一个参数并返回一个相同类型的参数

  ```java
  UnaryOperator<User> unaryOperator = (user) -> {
    user.setName("new name");
    return user;
  };
  ```

- BinaryOperator：代表一个接收两个参数并返回一个值的操作

  ```java
  BinaryOperator<Long> binaryOperator = (a, b) -> a + b;
  ```

- Supplier：代表一个函数，它提供了某种类型的值

  ```java
  Supplier<Integer> supplier = () -> (int) (Math.random() * 1000D);
  ```

- Consumer：代表一个接收一个参数而不返回任何值的函数

  ```java
  Consumer<Integer> consumer = (value) -> log.debug("{}", value);
  ```

#### 函数的组合

- and / or

  ```java
  Predicate<String> startsWithA = (text) -> text.startsWith("A");
  Predicate<String> endsWithX = (text) -> text.endsWith("X");
  Predicate<String> startsWithAAndEndsWithX = startsWithA.and(endsWithX);
  Predicate<String> startsWithAOrEndsWithX = startsWithA.or(endsWithX).negate();
  ```

- compose / andthen

  ```java
  Function<Integer, Integer> squareOp = (value) -> value * value;
  Function<Integer, Integer> doubleOp = (value) -> 2 * value;
  Function<Integer, Integer> doubleThenSquare = squareOp.compose(doubleOp); // doubleOp->squareOp
  Function<Integer, Integer> squareThenDouble = squareOp.andThen(doubleOp); // squareOp->doubleOp
  ```


## 第2章 重新认识”流“

> 本章主要是讲解 Java 中以流方式操作集合对象，以及在此过程中的一系列方便进行变换、过滤、排序、分组统计等操作符。同时介绍了 Java 中对于可能为空的对象的类型Optional以及它对函数的支持，帮助大家对“流”有更加清晰的认识！

### 2-1 什么是流和创建流的方式

```java
books.stream()
  .filter(b -> !"葵花宝典".equals(b.getTitle()))
  .sorted((b1, b2) -> b2.getPages().compareTo(b1.getPages()))
  .limit(3)
  .map(Book::getAuthor)
  .distinct()
  .forEach(System.out::println);
```

#### 建立流的几种方式

- Arrays.stream

  ```java
  List<User> list = Arrays.stream(arrayOfUsers)
    .peek(user -> log.debug("user: {}", user))
    .collect(Collectors.toList());
  ```

- Collection.stream

  ```java
  List<User> list = userList.stream()
    .peek(user -> log.debug("user: {}", user))
    .collect(Collectors.toList());
  ```

- Stream.of

  ```java
  List<User> list = Stream.of(arrayOfUsers[0], arrayOfUsers[1], arrayOfUsers[2])
    .peek(user -> log.debug("user: {}", user))
    .collect(Collectors.toList());
  ```

- Stream.iterate

  ```java
  List<Integer> list = Stream.iterate(0, n -> n + 1)
    .limit(10)
    .peek(n -> log.debug("the number is {}", n))
    .collect(Collectors.toList());
  ```

- Stream.generate

  ```java
  List<Double> list = Stream.generate(Math::random)
    .limit(10)
    .peek(n -> log.debug("the number is {}", n))
    .collect(Collectors.toList());
  ```

- StreamSupport.stream

  ```java
  Iterator<User> iterator = userList.iterator();
  Spliterator<User> spliterator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.NONNULL);
  Stream<User> userStream = StreamSupport.stream(spliterator, false);
  List<User> list = userStream
    .peek(user -> log.debug("user: {}", user))
    .collect(Collectors.toList());
  ```

- IntStream

  ```java
  List<Integer> list = IntStream.rangeClosed(0, 5)
    .boxed()
    .peek(i -> log.debug("the number is {}", i))
    .collect(Collectors.toList());
  ```

- Stream.builder()

  ```java
  Stream.Builder<User> userStreamBuilder = Stream.builder();
  List<User> list = userStreamBuilder
    .add(arrayOfUsers[0])
    .add(arrayOfUsers[1])
    .add(arrayOfUsers[2])
    .build()
    .skip(1) // 跳过1个元素
    .peek(user -> log.debug("user: {}", user))
    .collect(Collectors.toList());
  ```

### 2-2 基础操作符

filter、map、peek、findAny、findFirst

forEach、anyMatch、noneMatch、allMatch

count、min、max

### 2-3 Optional 流 - 异常和空值处理

isPresent、isEmpty

orElse、orElseGet、orElseThrow、or

ifPresent、ifPresentOrElse

### 2-4 收集器 - 集合对象收集器

> 收集为一个集合对象 - toList、toSet、toMap、toCollection

- toMap

  ```java
  TreeMap<String, User> sortedMap = Stream.concat(userList.stream(), userList.stream())
    .peek(user -> log.debug("username, {}", user.getUsername()))
    .collect(Collectors.toMap(
      User::getUsername, // key
      user -> user, // value
      (existing, replace) -> existing, // 合并策略
      TreeMap::new // 类型
    ));
  ```

- toCollection

  ```java
  TreeSet<User> users = userList.stream()
    .collect(Collectors.toCollection(
      () -> new TreeSet<>(Comparator.comparing(User::getAge)) // 类型
    ));
  ```

### 2-5 分组统计和聚合函数

> 聚合计算 - averagingXXX、summingXXX、maxBy、counting
>
> 分组统计 - groupingBy

- averagingXXX：平均数

  ```java
  double avg = userList.stream().collect(Collectors.averagingDouble(User::getAge));
  ```

- summingXXX：和

  ```java
  int sum = userList.stream().collect(Collectors.summingInt(User::getAge));
  int sum = userList.stream().mapToInt(User::getAge).sum();
  ```

- summarizingXXX：统计（和、平均数、最大值、最小值、计数）

  ```java
  DoubleSummaryStatistics stat = userList.stream().collect(Collectors.summarizingDouble(User::getAge));
  DoubleSummaryStatistics stat = userList.stream().mapToDouble(User::getAge).summaryStatistics();
  ```

- groupingBy

  ```java
  Map<Integer, List<User>> map1 = userList.stream().collect(
    Collectors.groupingBy(
      user -> (int) Math.floor(user.getAge() / 10.0) * 10
    )
  );
  ```

  ```java
  Map<Integer, DoubleSummaryStatistics> map2 = userList.stream().collect(
    Collectors.groupingBy(
      user -> (int) Math.floor(user.getAge() / 10.0) * 10,
      HashMap::new,
      Collectors.summarizingDouble(User::getAge)
    )
  );
  ```

  ```java
  Map<Integer, List<UserDto>> result = userList.stream().collect(
    Collectors.groupingBy(
      user -> (int) Math.floor(user.getAge() / 10.0) * 10,
      Collectors.mapping(
        user -> new UserDto(user.getId(), user.getUsername() + ":" + user.getName()),
        Collectors.toList()
      )
    )
  );
  ```

### 2-6 收集器 mapping, joining 和 collectingAndThen

> 其他操作 - mapping、collectingAndThen、joining

- mapping

  ```java
  List<String> strings = List.of("bb", "ddd", "cc", "a");
  Map<Integer, TreeSet<String>> result = strings.stream().collect(
    Collectors.groupingBy(
      String::length,
      Collectors.mapping(
        String::toUpperCase,
        Collectors.filtering(
          s -> s.length() > 1,
          Collectors.toCollection(TreeSet::new)
        )
      )
    )
  );
  ```

- collectingAndThen

  ```java
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
  ```

- joining

  ```java
  String url = requestParams.entrySet().stream()
    .map(entry -> entry.getKey() + "=" + entry.getValue())
    .sorted()
    .collect(Collectors.joining("&", "http://local.dev/api?", ""));
  ```

### 2-7 排序

- 简单类型使用sorted

  ```java
  List<String> sortedListDefaultSortOperator = list.stream()
    .sorted()
    .collect(Collectors.toList());
  ```

- sorted可以传入Comparator

  ```java
  List<String> sortedListWithFunction = list.stream()
    // .sorted((a, b) -> a.compareTo(b))
    // .sorted(Comparator.naturalOrder())
    .sorted(String::compareTo)
    .collect(Collectors.toList());
  ```

- 倒序

  ```java
  List<String> descSortedListWithComparator = list.stream()
    // .sorted((a, b) -> b.compareTo(a))
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.toList());
  ```

- 自定义排序

  ```java
  List<User> descSortedListWithComparatorComparing = userList.stream()
    .sorted(Comparator.comparing(
      // user -> user.getUsername(), (a, b) -> a.compareTo(b)
      // User::getName, Collator.getInstance(Locale.SIMPLIFIED_CHINESE)
      User::getUsername, String::compareTo
    ))
    .collect(Collectors.toList());
  ```


## 第3章 流的高级操作

> 本章介绍高阶操作符Reduce 操作符，并结合一个实战小例子说明在实际开发中如何使用函数式编程，帮助大家进一步加强认识！

### 3-1 高阶操作符 flatMap

> flatMap - 处理流的嵌套

#### 父子对象常见的集合属性

```java
List<String> roleList = userList.stream()
  .flatMap(user -> user.getRoles().stream())
  .peek(role -> log.debug("role: {}", role))
  .collect(Collectors.toList());
```

#### 在流中产生了Optional元素

```java
long count = userList.stream()
  .map(user -> ThirdPartyApi.findByUsername(user.getUsername()))
  .flatMap(Optional::stream)
  // .flatMap(user -> ThirdPartyApi.findByUsername(user.getUsername()).stream())
  .peek(user -> log.debug("user: {}", user))
  .count();
```

### 3-2 reduce

> 执行归集操作 - 某种程度上和Collect作用类似

```java
Integer sumByReduce = userList.stream()
  .map(User::getAge)
  // .reduce(0, (Integer acc, Integer curr) -> Integer.sum(acc, curr));
  // .reduce(0, (acc, curr) -> acc + curr);
  .reduce(0, Integer::sum);

MutableInt sumByCollect = userList.stream()
  .collect(MutableInt::new,
           (MutableInt container, User user) -> container.add(user.getAge()),
           MutableInt::add);
```

### 3-3 实际应用的难点和知识回顾

```java
private Function<User, ResponseEntity<UserDTO>> mapUserToDto() {
  return user -> {
    UserDTO dto = UserDTO.builder()
      .username(user.getUsername())
      .name(user.getName())
      .enabled(user.isEnabled() ? "激活" : "禁用")
      .mobile(user.getMobile())
      .build();
    return ResponseEntity.ok().body(dto);
  };
}
```

```java
private Function<User, User> saveUser(UpdateUserDTO updateUserDTO) {
  return user -> {
    User toSave = user
      .withMobile(updateUserDTO.getMobile())
      .withName(updateUserDTO.getName())
      .withEmail(updateUserDTO.getEmail());
    return userRepo.save(toSave);
  };
}
```

```java
userRepo.findOptionalByUsername(username)
  .map(saveUser(updateUserDTO))
  .map(mapUserToDto())
  .orElse(ResponseEntity.notFound().build());
```

```java
mapUserToDto().apply(saved);
```

