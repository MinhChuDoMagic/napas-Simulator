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
public class AsyncConfigurationSend {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfigurationSend.class);
    @Value("${sendMessageThread.coreThread}")
    private int coreThread;
    @Value("${sendMessageThread.maxThread}")
    private int maxThread;
    @Value("${sendMessageThread.queue}")
    private int queue;

    @Bean(name = "sendTaskExecutor")
    public Executor taskExecutor() {
        LOGGER.debug("Creating Async Task Executor");
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreThread);
        executor.setMaxPoolSize(maxThread);
        executor.setQueueCapacity(queue);
        executor.setThreadNamePrefix("SendThread-");
        executor.initialize();
        return executor;
    }

}
