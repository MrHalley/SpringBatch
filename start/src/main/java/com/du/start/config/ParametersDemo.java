package com.du.start.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Du
 * @date 2020/10/20 15:02
 *
 * bug不知道参数怎么传进来的
 */
@Configuration
@Slf4j
public class ParametersDemo implements StepExecutionListener {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private Map<String,JobParameter> parameters;

    public ParametersDemo(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step parameterStep(){
        return stepBuilderFactory.get("parameterStep")
                .listener(this)
                .tasklet((contribution,chunkContext)->{
                    log.info("parameterStep");
                    log.info(String.valueOf(parameters.get("info")));
                    return RepeatStatus.FINISHED;
                }).build();
    }

    /**
     * @return
     */
    @Bean
    public Job parameterJob(){
        return jobBuilderFactory.get("parameterJob")
                .start(parameterStep())
                .build();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        parameters = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
