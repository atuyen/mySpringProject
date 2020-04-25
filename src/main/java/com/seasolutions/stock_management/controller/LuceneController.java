package com.seasolutions.stock_management.controller;


import com.seasolutions.stock_management.model.event.RexindexSearch;
import com.seasolutions.stock_management.security.annotation.Unauthenticated;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.payloads.AveragePayloadFunction;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LuceneController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Unauthenticated
    @GetMapping("/reindex")
    public String reindexSearch(){
        eventPublisher.publishEvent(new RexindexSearch());
        return  "Caches cleared";
    }








}
