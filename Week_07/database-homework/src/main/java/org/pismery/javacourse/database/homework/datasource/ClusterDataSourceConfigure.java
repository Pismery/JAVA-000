package org.pismery.javacourse.database.homework.datasource;


import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.ReplicaQueryRuleConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.rule.ReplicaQueryDataSourceRuleConfiguration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class ClusterDataSourceConfigure {

    @Bean
    @ConfigurationProperties("primary.datasource")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("primaryDataSource")
    @Primary
    public DataSource primaryDataSource() {
        DataSourceProperties dataSourceProperties = primaryDataSourceProperties();
        log.info("primary datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("secondary.datasource")
    public List<DataSourceProperties> secondaryDataSourceProperties() {
        return new ArrayList<>();
    }

    @Bean("secondaryDataSource")
    public List<DataSource> secondaryDataSource() {
        List<DataSourceProperties> dataSourceProperties = secondaryDataSourceProperties();
        dataSourceProperties.forEach(i -> log.info("secondary datasource: {}", i.getUrl()));

        return dataSourceProperties.stream().map(
                i -> i.initializeDataSourceBuilder().build()).collect(Collectors.toList()
        );
    }

}
