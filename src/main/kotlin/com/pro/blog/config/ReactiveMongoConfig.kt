package com.pro.blog.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mapping.model.SnakeCaseFieldNamingStrategy
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(basePackages = ["com.pro.blog.repository.mongo"])
class ReactiveMongoConfig: AbstractReactiveMongoConfiguration() {

    @Value("\${spring.data.mongodb.uri}")
    var mongoUri: String? = null

    @Value("\${spring.data.mongodb.database}")
    var dbname: String? = null

    override fun reactiveMongoClient(): MongoClient {
        return MongoClients.create(mongoUri)
    }

    override fun getDatabaseName(): String {
        return dbname!!
    }

    @Bean
    fun reactiveMongoTemplate(): ReactiveMongoTemplate? {
        return ReactiveMongoTemplate(
            reactiveMongoClient(),
            databaseName
        )
    }

    @Bean
    override fun mappingMongoConverter(
        databaseFactory: ReactiveMongoDatabaseFactory,
        customConversions: MongoCustomConversions,
        mappingContext: MongoMappingContext,
    ): MappingMongoConverter {
        mappingContext.setFieldNamingStrategy(SnakeCaseFieldNamingStrategy())
        return super.mappingMongoConverter(databaseFactory, customConversions, mappingContext)
    }
}