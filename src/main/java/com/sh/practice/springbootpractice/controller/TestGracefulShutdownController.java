package com.sh.practice.springbootpractice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.Executor;

/**
 * @author sh
 * @date 2021-03-21 15:49
 */
@RestController
@RequestMapping("/test")
public class TestGracefulShutdownController {
    @Autowired
    private Executor asyncServiceExecutor;
    @Value("${spring.lifecycle.timeout-per-shutdown-phase}")
    private Duration duration;

    @GetMapping("/shutdown")
    public String testShutdown() {
        for (int i = 0; i < 100; i++) {
            final int temp = i;
            asyncServiceExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + temp + duration.getSeconds());
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "success";
    }
}
