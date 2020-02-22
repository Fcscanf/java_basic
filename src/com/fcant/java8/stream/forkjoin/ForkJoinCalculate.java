package com.fcant.java8.stream.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinCalculate
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 21:42:00 2020/2/22/0022
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {

    public static final long serialVersionUID = 134656970987L;

    private Long start;
    private Long end;

    public ForkJoinCalculate(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    public static final long THRESHOLD = 10000;

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            // 拆分子任务，同时压入线程队列
            left.fork();

            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
            right.fork();

            return left.join() + right.join();
        }
    }
}
