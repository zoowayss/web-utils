package top.zoowayss.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.zoowayss.web.entity.District;

import java.util.Collection;

public interface DistrictMapper extends BaseMapper<District> {

    long saveBatch(Collection<District> saveList);
}