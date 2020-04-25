package com.seasolutions.stock_management.search.index;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Log4j2
@Component
public class OrientLuceneIndexer {
    @Value("${search.index.enabled}")
    private  boolean  enabled;

    @Value("${search.updateIndexInterval}")
    private static    final String updateIndexInterval="1000000";

    private boolean isUpdating = false;



    @Scheduled(initialDelay = 15000, fixedDelayString = updateIndexInterval)
    public void scheduleIndex(){
        startIndexing();
    }



    public  void startIndexing(){
        if (!this.enabled) {
            return;
        }
        if (isUpdating) {
            log.debug("Skip indexing, search index is already updating");
            return;
        }
        try{
            isUpdating = true;
            log.info("Start indexing....");



        }catch (Throwable t){

        }finally {
            isUpdating=false;
        }

    }

















}
