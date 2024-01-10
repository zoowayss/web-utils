package top.zoowayss.web.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.zoowayss.web.entity.Application;
import top.zoowayss.web.exception.AppException;
import top.zoowayss.web.service.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationHandlerInterceptor implements HandlerInterceptor {


    private ApplicationService applicationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            String appId = request.getHeader(Constants.APP_ID_NAME_KEY);
            if (!StringUtils.hasText(appId)) {
                throw new AppException(404, String.format("appId:%s not found", appId));
            }

            Application app = applicationService.findByAppId(appId);
            if (app == null) {
                throw new AppException(404, String.format("appId:%s not found", appId));
            }
            return true;
        }
        return true;
    }
}
