package com.du.start.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Du
 * @date 2020/10/20 8:47
 */
public class MyDecider implements JobExecutionDecider {
    private int count;
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        count ++;
        if(count % 2 == 0)
            return new FlowExecutionStatus("even");
        else
            return new FlowExecutionStatus("odd");

    }
}
