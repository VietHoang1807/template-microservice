package com.kk.common_lib;

import com.kk.common_lib.util.ExecutionPlan;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

@BenchmarkMode(Mode.AverageTime)
public class YourBenchmark {

    @Benchmark
    public String goodStringConcat(ExecutionPlan plan) {
        return plan.firstWord + plan.secondWord + plan.thirdWord;  // JVM can't pre-calculate this
    }

    @Benchmark
    public static String badStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append(" ");
        sb.append("World");
        return sb.toString();  // Still optimized because inputs are constants
    }
}
