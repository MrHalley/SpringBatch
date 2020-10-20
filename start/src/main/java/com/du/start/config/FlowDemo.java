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

/**
 * @author Mr.Du
 * @date 2020/10/20 8:15
 *
 * 将step装进入flow中来执行
 */
@Configuration
@EnableBatchProcessing
@Slf4j
public class FlowDemo {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public FlowDemo(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step flowDemoStep(){
        return stepBuilderFactory.get("flowDemoStep")
                .tasklet((contribution,chunkContext)->{
                    log.warn("flowDemoStep");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step flowDemoStep2(){
        return stepBuilderFactory.get("flowDemoStep2")
                .tasklet((contribution,chunkContext)->{
                    log.warn("flowDemoStep2");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step flowDemoStep3(){
        return stepBuilderFactory.get("flowDemoStep3")
                .tasklet((contribution,chunkContext)->{
                    log.warn("flowDemoStep3");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    /**
     * 创建Flow Step的集合，可以用于复用step
     * @return
     */
    @Bean
    public Flow flowDemoFlow(){
        return new FlowBuilder<Flow>("flowDemoFlow")
                .start(flowDemoStep())
                .next(flowDemoStep2())
                .build();
    }
    @Bean
    public Job flowDemoJob(){
        return jobBuilderFactory.get("flowDemoJob")
                .start(flowDemoFlow())
                .next(flowDemoStep3())
                .end()
                .build();
    }


}
