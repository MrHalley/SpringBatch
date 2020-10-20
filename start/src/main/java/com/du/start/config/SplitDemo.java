package com.du.start.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * @author Mr.Du
 * @date 2020/10/20 8:29
 * 利用split()实现 并发step,并发flow,，此案例中为每个flow一个线程
 */
@Configuration
@EnableBatchProcessing
@Slf4j
public class SplitDemo {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public SplitDemo(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }
    @Bean
    public Step splitStep1(){
        return stepBuilderFactory.get("splitStep1")
                .tasklet((contribution,chunkContext)->{
                    log.warn("splitStep1");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step splitStep2(){
        return stepBuilderFactory.get("splitStep2")
                .tasklet((contribution,chunkContext)->{
                    log.warn("splitStep2");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step splitStep3(){
        return stepBuilderFactory.get("splitStep3")
                .tasklet((contribution,chunkContext)->{
                    log.warn("splitStep3");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Flow splitFlow1(){
        return new FlowBuilder<Flow>("splitFlow1")
                .start(splitStep1())
                .end();
    }
    @Bean
    public Flow splitFlow2(){
        return new FlowBuilder<Flow>("splitFlow2")
                .start(splitStep2())
                .next(splitStep3())
                .end();
    }

    @Bean
    public Job splitJob(){
        return jobBuilderFactory.get("splitJob")
                .start(splitFlow1())
                .split(new SimpleAsyncTaskExecutor())
                .add(splitFlow2())
                .end().build();
    }
}
