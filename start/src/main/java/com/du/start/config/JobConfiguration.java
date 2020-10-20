package com.du.start.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.Du
 * @date 2020/10/19 18:59
 */
@Configuration
@EnableBatchProcessing
@Slf4j
public class JobConfiguration {
    //注入创建任务对象的对象
    private JobBuilderFactory jobBuilderFactory;
    //任务的执行由Step决定
    //注入创建Step对象的对象
    private StepBuilderFactory stepBuilderFactory;
    //创建任务对象

    public JobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job helloWorldJob(){
        return jobBuilderFactory.get("helloWorldJob")
                .start(step1())
                .build();
    }
    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .tasklet((contribution,chunkContext)->{
                    log.warn("Hello world");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
