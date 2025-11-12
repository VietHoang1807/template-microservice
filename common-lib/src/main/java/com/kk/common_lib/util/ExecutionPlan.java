package com.kk.common_lib.util;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class ExecutionPlan {
    public String firstWord = "Hello";
    public String secondWord = " ";
    public String thirdWord = "World";

    @Setup
    public void setUp() {
        // You could even randomize these values
        firstWord = "Hello" + System.nanoTime() % 2; // Prevents compile-time optimization
    }
}
