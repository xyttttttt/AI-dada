package com.xyt.dada;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RXJavaTest {

    @Test
    public void test() {
        //创建数据流
        Flowable<Long> flowable = Flowable.interval(1, TimeUnit.SECONDS)
                .map(i -> i + 1)
                .subscribeOn(Schedulers.io());//指定执行操作使用的线程池
        //订阅flowable, 并切打印出每个接收到的数字
        flowable
                .observeOn(Schedulers.io())
                .doOnNext(i -> System.out.println("Received: " + i))
                .subscribe();
        //主线程休眠
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
