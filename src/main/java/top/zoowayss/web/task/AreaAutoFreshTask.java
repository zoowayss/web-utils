package top.zoowayss.web.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.zoowayss.web.service.DistrictService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Component
@ConditionalOnProperty(prefix = "task.a-map", name = "enabled", havingValue = "true")
@Slf4j
public class AreaAutoFreshTask implements InitializingBean {

    @Resource
    private DistrictService districtService;


    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    public void refresh() {
        long s = System.currentTimeMillis();
        districtService.refreshAMap();
        log.info("aMap refresh task finished, cost {} ms", System.currentTimeMillis() - s);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("aMap refresh task enabled!");
    }
}
