package top.zoowayss.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zoowayss.web.domain.resp.ApiResult;
import top.zoowayss.web.service.DistrictService;
import top.zoowayss.web.service.ApplicationService;

import javax.annotation.Resource;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@RestController
public class TestController {


    @Resource
    private ApplicationService applicationService;

    @GetMapping("/app/ping")
    public ApiResult<?> testInterceptor() {
        return ApiResult.success("pong");
    }


    @GetMapping("ping")
    public String test() {
        return "pong";
    }

    @GetMapping("/create")
    public ApiResult<?> createApp() {
        return ApiResult.success(applicationService.createApp());
    }

    @Resource
    private DistrictService districtService;

    @GetMapping("/amap")
    public String amap() {
        districtService.refreshAMap();
        return "OK!";
    }
}
