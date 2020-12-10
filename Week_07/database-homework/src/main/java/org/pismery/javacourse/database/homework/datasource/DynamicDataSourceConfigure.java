package org.pismery.javacourse.database.homework.datasource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

//@Configuration
public class DynamicDataSourceConfigure {
    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Properties properties = new Properties();
        properties.setProperty("sqlType", "mysql");
        sqlSessionFactoryBean.setConfigurationProperties(properties);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean("dynamicTxManager")
    public DynamicDataSourceTransactionManager dynamicTxManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
        DynamicDataSourceTransactionManager dynamicDataSourceTransactionManager = new DynamicDataSourceTransactionManager();
        dynamicDataSourceTransactionManager.setDataSource(dataSource);
        return dynamicDataSourceTransactionManager;
    }

    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(@Qualifier("primaryDataSource") DataSource primaryDataSource,
                                               @Qualifier("secondaryDataSource") List<DataSource> secondaryDataSources) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //
        targetDataSources.put("master", primaryDataSource);

        List<Object > slaveDataSourceKeys = new ArrayList<>(secondaryDataSources.size());
        for (int i = 0; i < secondaryDataSources.size(); i++) {
            String key = "slave" + i;
            targetDataSources.put(key, secondaryDataSources.get(i));
            slaveDataSourceKeys.add(key);
        }

        dynamicDataSource.setTargetDataSources(targetDataSources);

        dynamicDataSource.setDefaultTargetDataSource(primaryDataSource);
        dynamicDataSource.addSlaveDataSources(slaveDataSourceKeys);

        return dynamicDataSource;

    }
}
