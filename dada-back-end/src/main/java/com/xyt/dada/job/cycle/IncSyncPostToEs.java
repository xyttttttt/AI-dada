package com.xyt.dada.job.cycle;


import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 增量同步帖子到 es
 *
 * @author 薛宇彤
 * @from 1604899092
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class IncSyncPostToEs {




    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void run() {
    }
}
