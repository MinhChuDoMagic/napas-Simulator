package com.opw.NapasSimulator.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync(proxyTargetClass = true)
public class AsyncConfigurationGet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfigurationGet.class);
    @Value("${getMessageThread.coreThread}")
    private int coreThread;
    @Value("${getMessageThread.maxThread}")
    private int maxThread;
    @Value("${getMessageThread.queue}")
    private int queue;

    @Bean(name = "getTaskExecutor")
    public Executor taskExecutor() {
        LOGGER.debug("Creating Async Task Executor");
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreThread);
        executor.setMaxPoolSize(maxThread);
        executor.setQueueCapacity(queue);
        executor.setThreadNamePrefix("GetThread-");
        executor.initialize();
        return executor;
    }
}
