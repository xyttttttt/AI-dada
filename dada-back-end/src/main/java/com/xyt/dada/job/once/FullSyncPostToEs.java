package com.xyt.dada.job.once;


import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/**
 * 全量同步帖子到 es
 *
 * @author 薛宇彤
 * @from 1604899092
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FullSyncPostToEs implements CommandLineRunner {
    

    @Override
    public void run(String... args) throws Exception {

    }
}
