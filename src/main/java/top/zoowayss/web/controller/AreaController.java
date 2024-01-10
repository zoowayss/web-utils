package top.zoowayss.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zoowayss.web.config.AppId;
import top.zoowayss.web.domain.resp.ApiResult;
import top.zoowayss.web.service.DistrictService;

import javax.annotation.Resource;

/**
 * 高德地图
 *
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */

@RestController
@RequestMapping("/app/area")
public class AreaController {

    @Resource
    private DistrictService districtService;

    /**
     * 通过 city 获取地图信息
     * @param appid
     * @param city
     * @return
     */
    @GetMapping("/{city}")
    public ApiResult<?> list(@AppId String appid, @PathVariable String city) {
        return ApiResult.success(districtService.listCity(city));
    }
}
