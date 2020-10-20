package com.du.start.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.Du
 * @date 2020/10/20 9:13
 */
@Configuration
@Slf4j
public class ChildJob2 {
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    public ChildJob2(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Step child2Step(){
        return stepBuilderFactory.get("child2Step")
                .tasklet((contribution,chunkContext)->{
                    log.warn("child2Step");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step child2Step2(){
        return stepBuilderFactory.get("child2Step2")
                .tasklet((contribution,chunkContext)->{
                    log.warn("child2Step2");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job child2Job(){
        return jobBuilderFactory.get("child2Job")
                .start(child2Step())
                .next(child2Step2())
                .build();
    }
}
