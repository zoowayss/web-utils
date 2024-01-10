package top.zoowayss.web.config;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.zoowayss.web.service.ApplicationService;

import java.util.List;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ApplicationHandlerInterceptor applicationHandlerInterceptor(ApplicationService applicationService) {
        return new ApplicationHandlerInterceptor(applicationService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(SpringUtil.getBean(ApplicationHandlerInterceptor.class)).addPathPatterns("/app/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AppIdMethodArgumentResolver());
    }
}
