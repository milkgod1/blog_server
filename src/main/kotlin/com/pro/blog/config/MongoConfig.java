package com.pro.blog.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


//@Configuration
//@EnableReactiveMongoRepositories(basePackages = "com.pro.blog.repository.mongo")
public class MongoConfig {

//    @Value("${spring.data.mongodb.uri}")
//    String mongoUri;
//
//    @Value("${spring.data.mongodb.database}")
//    String dbname;
//
//    @NotNull
//    @Override
//    public MongoClient reactiveMongoClient() {
//        return MongoClients.create(mongoUri);
//    }
//
//    @NotNull
//    @Override
//    protected String getDatabaseName() {
//        return dbname;
//    }
//
//    @Bean
//    public ReactiveMongoTemplate reactiveMongoTemplate() {
//        return new ReactiveMongoTemplate(
//                reactiveMongoClient(),
//                getDatabaseName()
//        );
//    }
}
