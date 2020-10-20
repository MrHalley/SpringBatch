package com.du.start.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Du
 * @date 2020/10/20 13:43
 */
@Slf4j
@Component
public class MyChunkListener{
    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext){
        log.warn(chunkContext.getStepContext().getStepName()+">>>before...");
    }
    @AfterChunk
    public void afterChunk(ChunkContext chunkContext){
        log.warn(chunkContext.getStepContext().getStepName()+">>>after...");
    }
}
