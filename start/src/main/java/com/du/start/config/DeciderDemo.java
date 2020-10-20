package com.du.start.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.batch.api.Decider;

/**
 * @author Mr.Du
 * @date 2020/10/20 8:43
 */
@Configuration
@EnableBatchProcessing
@Slf4j
public class DeciderDemo {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public DeciderDemo(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step deciderStep(){
        return stepBuilderFactory.get("deciderStep")
                .tasklet((contribution,chunkContext)->{
                    log.warn("deciderStep");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step deciderStep2(){
        return stepBuilderFactory.get("deciderStep2")
                .tasklet((contribution,chunkContext)->{
                    log.warn("deciderStep2");
                    return RepeatStatus.FINISHED;
                }).build();
    }
    @Bean
    public Step deciderStep3(){
        return stepBuilderFactory.get("deciderStep3")
                .tasklet((contribution,chunkContext)->{
                    log.warn("deciderStep3");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public JobExecutionDecider myDecider(){
        return new MyDecider();
    }

    @Bean
    public Job deciderJob(){
        return jobBuilderFactory.get("deciderJob")
                .start(deciderStep())
                .next(myDecider())
                .from(myDecider()).on("even").to(deciderStep2())
                .from(myDecider()).on("odd").to(deciderStep3())
                .from(deciderStep3()).on("*").to(myDecider())//回到myDecider(),即上面next处
                .end().build();
    }

}
