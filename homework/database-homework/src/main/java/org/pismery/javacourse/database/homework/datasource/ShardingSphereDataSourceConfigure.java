package org.pismery.javacourse.database.homework.datasource;

import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.RuleConfiguration;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.ReplicaQueryRuleConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.rule.ReplicaQueryDataSourceRuleConfiguration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Configuration
public class ShardingSphereDataSourceConfigure {
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("shardingSphereDataSource") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Properties properties = new Properties();
        properties.setProperty("sqlType", "mysql");
        sqlSessionFactoryBean.setConfigurationProperties(properties);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean("shardingSphereTxManager")
    public DataSourceTransactionManager shardingSphereTxManager(@Qualifier("shardingSphereDataSource") DataSource dataSource) {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource);
        return txManager;
    }

    @Bean("shardingSphereDataSource")
    public DataSource shardingSphereDataSource(@Qualifier("primaryDataSource") DataSource primaryDataSource,
                                               @Qualifier("secondaryDataSource") List<DataSource> secondaryDataSources) throws SQLException {
        // 构建数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", primaryDataSource);
        List<String> replicaDataSourceNames = new ArrayList<>();
        for (int i = 0; i < secondaryDataSources.size(); i++) {
            String key = "slave" + i;
            dataSourceMap.put(key, secondaryDataSources.get(i));
            replicaDataSourceNames.add(key);
        }

        // 构建配置规则
        List<ReplicaQueryDataSourceRuleConfiguration> configurations = Collections.singletonList(
                new ReplicaQueryDataSourceRuleConfiguration("ds", "master", replicaDataSourceNames, "load_balancer")
        );

        HashMap<String, ShardingSphereAlgorithmConfiguration> loadBalancers = new HashMap<>();
        loadBalancers.put("load_balancer", new ShardingSphereAlgorithmConfiguration("ROUND_ROBIN", new Properties()));

        Set<RuleConfiguration> ruleConfiguration = Collections.singleton(
                new ReplicaQueryRuleConfiguration(configurations, loadBalancers)
        );

        Properties props = new Properties();
        props.put("sql-show", true);
        props.put("sql-simple", true);

        return ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, ruleConfiguration, props);
    }
}
