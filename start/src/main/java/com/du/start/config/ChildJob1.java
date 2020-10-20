package com.du.start.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.Du
 * @date 2020/10/20 9:13
 */
@Configuration
@Slf4j
public class ChildJob1 {
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    public ChildJob1(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Step child1Step(){
        return stepBuilderFactory.get("child1Step")
                .tasklet((contribution,chunkContext)->{
                    log.warn("child1Step");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job child1Job(){
        return jobBuilderFactory.get("child1Job")
                .start(child1Step())
                .build();
    }
}
