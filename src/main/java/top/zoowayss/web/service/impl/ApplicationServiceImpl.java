package top.zoowayss.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.zoowayss.web.entity.Application;
import top.zoowayss.web.mapper.ApplicationMapper;
import top.zoowayss.web.service.ApplicationService;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {
    @Override
    public Application findByAppId(String appId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Application>().eq(Application::getAppId, appId));
    }

    @Override
    public String createApp() {
        SecureRandom random = new SecureRandom();
        Base64.Encoder encoder = Base64.getEncoder();
        String appId = encoder.encodeToString(random.generateSeed(16));
        String secret = encoder.encodeToString(random.generateSeed(16));
        Application application = Application.builder().appId(appId).secret(secret).userId("admin").enabled(true).build();
        save(application);
        return appId;
    }
}
