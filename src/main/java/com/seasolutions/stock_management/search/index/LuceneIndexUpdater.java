package com.seasolutions.stock_management.search.index;


import com.seasolutions.stock_management.model.event.RexindexSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class LuceneIndexUpdater {



    @Autowired
    OrientLuceneIndexer indexer;


    @EventListener
    public void reIndex(RexindexSearch event){
        indexer.startIndexing();
    }


    @EventListener
    public void reIndex2(RexindexSearch event){
        System.out.println("Reindex search start 2");
    }


}
