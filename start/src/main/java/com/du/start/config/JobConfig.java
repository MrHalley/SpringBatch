package com.du.start.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.Du
 * @date 2020/10/19 18:59
 *
 * 简单决策 on
 */
@Configuration
@EnableBatchProcessing
@Slf4j
public class JobConfig {
    //注入创建任务对象的对象
    private JobBuilderFactory jobBuilderFactory;
    //任务的执行由Step决定
    //注入创建Step对象的对象
    private StepBuilderFactory stepBuilderFactory;
    //创建任务对象

    public JobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job jobDemoJob(){
        return jobBuilderFactory.get("jobDemoJob")
//                .start(step())
//                .next(step2())
//                .next(step3())
                .start(step())
                .on("COMPLETED").to(step2())
                .from(step2()).on("COMPLETED").to(step3())// fail() stopAndRestart()
                .from(step3()).end()
                .build();
    }
    @Bean
    public Step step(){
        return stepBuilderFactory.get("step")
                .tasklet((contribution,chunkContext)->{
                    log.warn("step1");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step step2(){
        return stepBuilderFactory.get("step2")
                .tasklet((contribution,chunkContext)->{
                    log.warn("step2");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean Step step3(){
        return stepBuilderFactory.get("step3")
                .tasklet((contribution,chunkContext)->{
                    log.warn("step3");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
