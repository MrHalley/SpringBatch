package com.du.start;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class StartApplication {

    public static void main(String[] args) {
        args = new String[]{"info","ParameterValue"};
        SpringApplication.run(StartApplication.class, args);
    }

}
