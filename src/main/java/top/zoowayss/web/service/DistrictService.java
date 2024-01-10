package top.zoowayss.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import lombok.SneakyThrows;
import top.zoowayss.web.entity.District;

import java.util.List;

/**
 * @Description:
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/11/2 16:36
 */

public interface DistrictService extends IService<District> {
    @SneakyThrows
    void refreshAMap();

    List<District> listCity(String cid);

    List<District> likeName(String name);
}
