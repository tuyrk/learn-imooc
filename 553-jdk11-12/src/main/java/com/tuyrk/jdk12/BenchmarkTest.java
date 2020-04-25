package com.tuyrk.jdk12;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 4-1 微基准测试
 * JMH使用方法：测试字符串拼接的性能
 *
 * @author tuyrk
 */
@Fork(2)
@Threads(8)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Measurement(iterations = 10, time = 5)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchmarkTest {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BenchmarkTest.class.getSimpleName())
                // .output("benchmark.log")
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public void testStringAdd() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;
        }
    }

    @Benchmark
    public void testStringAdds() {
        for (int i = 0; i < 10; i++) {
            String a = "" + "1" + "2" + "3" + "4" + "5" + "6" + "7" + "8" + "9";
        }
    }

    @Benchmark
    public void testStringBuilderAdd() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
    }
}
/*
Benchmark                            Mode  Cnt         Score         Error   Units
BenchmarkTest.testStringAdd         thrpt   20     22305.916 ±    2473.762  ops/ms
BenchmarkTest.testStringAdds        thrpt   20  11795913.631 ± 1105845.467  ops/ms
BenchmarkTest.testStringBuilderAdd  thrpt   20     86506.156 ±    3584.597  ops/ms
* */
