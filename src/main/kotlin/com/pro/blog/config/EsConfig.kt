package com.pro.blog.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories

@Configuration
@EnableReactiveElasticsearchRepositories(basePackages = ["com.pro.blog.repository.es"])
@ComponentScan
class EsConfig : AbstractReactiveElasticsearchConfiguration() {

    override fun reactiveElasticsearchClient(): ReactiveElasticsearchClient {
        val clientConfig = ClientConfiguration.builder()
            .connectedTo("localhost:9200")
            .build()
        return ReactiveRestClients.create(clientConfig)
    }
}