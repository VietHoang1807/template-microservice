package com.kk.common_lib;

import org.openjdk.jmh.profile.AsyncProfiler;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormat;
import org.openjdk.jmh.results.format.ResultFormatFactory;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;

public class CommonLibApplication {

	public static void main(String[] args) throws RunnerException, IOException {
        var builder = new OptionsBuilder()
                .include(YourBenchmark.class.getSimpleName())
                .warmupIterations(10) // Chạy 10 vòng làm nóng JVM (JIT optimization ổn định)
                .warmupTime(TimeValue.seconds(1)) // Mỗi vòng warmup chạy 1 giây
                .measurementIterations(10) // Thực hiện đo chính xác 10 lần
                .measurementTime(TimeValue.seconds(2)) // Mỗi lần đo chạy 2 giây
                .forks(2) // Chạy JVM độc lập (để tránh ảnh hưởng trạng thái JIT hoặc GC giữa các test)
                .shouldDoGC(true) // Ép JVM chạy GC trước mỗi vòng
                .shouldFailOnError(true); // Nếu có lỗi trong benchmark → fail ngay thay vì bỏ qua;
        // Add profiler based on OS
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            builder.addProfiler(GCProfiler.class);
            builder.addProfiler(StackProfiler.class);
        } else {
            builder.addProfiler(AsyncProfiler.class, "lock=1ms simple=true output=flamegraph");
        }

        Options opt = builder.build();
        var results = new Runner(opt).run();

        results.forEach(runResult -> {
            var result = runResult.getPrimaryResult();
            System.out.printf("Benchmark: %s | Score: %.3f %s%n",
                    runResult.getParams().getBenchmark(),
                    result.getScore(),
                    result.getScoreUnit());
        });
        // 🔹 Xuất ra file CSV và JSON
        exportResults(results, "benchmark-results.csv", ResultFormatType.CSV);
        exportResults(results, "benchmark-results.json", ResultFormatType.JSON);

        results.stream()
                .sorted(Comparator.comparingDouble(a -> a.getPrimaryResult().getScore()))
                .forEachOrdered(result -> {
                    String name = result.getParams().getBenchmark();
                    double score = result.getPrimaryResult().getScore();
                    System.out.printf("🔹 %-60s : %.10f s/op%n", name, score);
                });
	}

    private static void exportResults(Collection<RunResult> results, String fileName, ResultFormatType formatType) throws IOException {
        File file = new File(fileName);
        ResultFormat resultFormat = ResultFormatFactory.getInstance(formatType, String.valueOf(file));
        resultFormat.writeOut(results);
        System.out.println("✅ Exported benchmark results to: " + file.getAbsolutePath());
    }
}
