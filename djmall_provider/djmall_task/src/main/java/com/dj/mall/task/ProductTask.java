/*
 * 作者：CF
 * 日期：2020-07-27 15:30
 * 项目：djmall
 * 模块：djmall_task
 * 类名：ProductTask
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.task;

import com.dj.mall.model.util.HttpClientUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author chengf
 * @date 2020/7/27 15:30
 * 商品solr索引
 */
@Component
public class ProductTask {
    /**
     * 定时增量索引 4小时执行一次
     * @throws Exception 异常信息
     */
    @Scheduled(cron = "0 0 0-4 * * ? ")
    public void timedIncrementalIndex() throws Exception {
        HttpClientUtil.sendHttpRequest("http://localhost:8085/solr/SolrCore/dataimport?command=delta-import", HttpClientUtil.HttpRequestMethod.GET, new HashMap<>());
    }

    /**
     * 定时增量索引 8小时执行一次
     * @throws Exception 异常信息
     */
    @Scheduled(cron = "0 0 0-8 * * ? ")
    public void rebuildIndexRegularly() throws Exception {
        HttpClientUtil.sendHttpRequest("http://localhost:8085/solr/SolrCore/dataimport?command=full-import", HttpClientUtil.HttpRequestMethod.GET, new HashMap<>());
    }
}
