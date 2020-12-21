package org.pismery.javacourse.database.homework.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 从数据源
     */
    private final List<Object> slaveDataSources = new ArrayList<>();

    /**
     * 轮询计数
     */
    private AtomicInteger sequence = new AtomicInteger(0);

    @Override
    protected Object determineCurrentLookupKey() {
        Object key = "";

        if (DynamicDataSourceHolder.isMaster()) {
            key = DynamicDataSourceHolder.MASTER;
        } else {
            //从库
            key = getSlaveKey();
        }
        System.out.println(String.format("==> select datasource key [%s]", key));
        //log.info("==> select datasource key [{}]",key);

        return key;
    }

    public void addSlaveDataSources(List<Object> slaveDataSources) {
        this.slaveDataSources.addAll(slaveDataSources);
    }

    private Object getSlaveKey() {
        if (sequence.intValue() == Integer.MAX_VALUE) {
            synchronized (sequence) {
                if (sequence.intValue() == Integer.MAX_VALUE) {
                    sequence = new AtomicInteger(0);
                }
            }
        }

        int idx = sequence.getAndIncrement() % slaveDataSources.size();
        return slaveDataSources.get(idx);
    }
}
