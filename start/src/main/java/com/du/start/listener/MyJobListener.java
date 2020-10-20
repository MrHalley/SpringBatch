package com.du.start.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Du
 * @date 2020/10/20 13:41
 *
 * 创建监听器，监听Job
 */
@Slf4j
@Component
public class MyJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.warn(jobExecution.getJobInstance().getJobName()+">>>before...");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.warn(jobExecution.getJobInstance().getJobName()+">>>after...");
    }
}
