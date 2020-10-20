package com.du.start.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Mr.Du
 * @date 2020/10/20 9:18
 */

@Slf4j
@Configuration
public class ParentJobDemo {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Job child1Job;
    private final Job child2Job;
    private final JobLauncher jobLauncher;
    public ParentJobDemo(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, Job child1Job, Job child2Job, JobLauncher jobLauncher) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.child1Job = child1Job;
        this.child2Job = child2Job;
        this.jobLauncher = jobLauncher;
    }

    @Bean
    public Job parentJob(JobRepository jobRepository,PlatformTransactionManager manager){
        return jobBuilderFactory.get("parentJob")
                .start(childJob1(jobRepository,manager))
                .next(childJob2(jobRepository,manager)).build();
    }
    //返回Job类型的Step,特殊的Step
    private Step childJob1(JobRepository jobRepository, PlatformTransactionManager manager){
        return new JobStepBuilder(new StepBuilder("childJob1"))
                .job(child1Job)
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(manager)
                .build();
    }

    private Step childJob2(JobRepository jobRepository, PlatformTransactionManager manager){
        return new JobStepBuilder(new StepBuilder("childJob2"))
                .job(child2Job)
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(manager)
                .build();
    }
}
