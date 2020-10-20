package com.du.start.config;

import com.du.start.listener.MyChunkListener;
import com.du.start.listener.MyJobListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mr.Du
 * @date 2020/10/20 13:46
 */
@Configuration
@Slf4j
public class ListenerDemo {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MyJobListener myJobListener;
    private final MyChunkListener myChunkListener;
    public ListenerDemo(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MyJobListener myJobListener, MyChunkListener myChunkListener) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.myJobListener = myJobListener;
        this.myChunkListener = myChunkListener;
    }

    private ItemWriter<String> write() {
        return (items)->{
            for (String item : items) {
                log.info(item);
            }
        };
    }

    private ItemReader<String> read() {
        return new ListItemReader<>(Arrays.asList("java","spring","mybatis"));
    }

    @Bean
    public Step listenerStep(){
        return stepBuilderFactory.get("ListenerStep")
                //read process write  每操作两次提交一次，读取,写入类型都为String
                .<String,String>chunk(2)
                .faultTolerant()
                .listener(myChunkListener)
                .reader(read())
                .writer(write())
                .build();
    }

    @Bean
    public Job listenerJob(){
        return jobBuilderFactory.get("ListenerJob")
                .start(listenerStep())
                .listener(myJobListener)
                .build();
    }

}
