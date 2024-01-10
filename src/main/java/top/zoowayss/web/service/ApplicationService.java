package top.zoowayss.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.zoowayss.web.entity.Application;

/**
 * @Description:
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/11/2 15:27
 */

public interface ApplicationService extends IService<Application> {
    Application findByAppId(String appId);

    String createApp();
}
