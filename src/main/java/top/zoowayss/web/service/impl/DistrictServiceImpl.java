package top.zoowayss.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.fluent.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.zoowayss.web.domain.Pair;
import top.zoowayss.web.domain.dto.DistrictDto;
import top.zoowayss.web.domain.enums.DistrictLevelEnum;
import top.zoowayss.web.domain.resp.AMapResponse;
import top.zoowayss.web.entity.District;
import top.zoowayss.web.mapper.DistrictMapper;
import top.zoowayss.web.service.DistrictService;
import top.zoowayss.web.utils.AssertUtil;
import top.zoowayss.web.utils.JsonUtils;

import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Service
@Slf4j
public class DistrictServiceImpl extends ServiceImpl<DistrictMapper, District> implements DistrictService {
    @Value("${amap.key}")
    private String amapKey;
    @Value("${amap.request-url}")
    public String requestUrl;

    @Override
    public boolean saveBatch(Collection<District> entityList) {
        Collection<District> saveList = new ArrayList<>(1000);
        if (CollectionUtils.isEmpty(entityList)) {
            return false;
        }

        Iterator<District> itr = entityList.iterator();
        while (itr.hasNext()) {
            saveList.add(itr.next());
            if (saveList.size() == 1000) {
                if (baseMapper.saveBatch(saveList) <= 0) {
                    throw new RuntimeException("save district data to db failed");
                }
                saveList = new ArrayList<>(1000);
            }
        }

        if (saveList.size() > 0) {
            if (baseMapper.saveBatch(saveList) <= 0) {
                throw new RuntimeException("save district data to db failed");
            }
        }

        return true;
    }

    /**
     * 从高德地图api获取行政区划数据
     * <p>
     * redis 存储结构： key_prefix:province:city:district
     * 武汉市：
     * key_prefix:湖北省:武汉:null -> id
     */
    @SneakyThrows
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshAMap() {
        String aMapRespStr = Request.get(String.format(requestUrl, URLEncoder.encode("中国"), amapKey)).execute().returnContent().asString();

//        new FileOutputStream("/Users/jalivv/Desktop/amap.json",false).write(aMapRespStr.getBytes());
//        byte[] bytes = new FileInputStream("/Users/jalivv/Desktop/amap.json").readAllBytes();
//        String aMapRespStr = new String(bytes);
        AMapResponse<DistrictDto> mapResponse = JsonUtils.toObj(aMapRespStr, new TypeReference<>() {
        });
        AssertUtil.isTrue(1 == mapResponse.getStatus(), "load district data from gaoDe api failed");
        List<DistrictDto> districts = mapResponse.getDistricts();
        List<District> saveList = new ArrayList<>(44000);
//        List<DoubleField<String, DistrictDto>> keys = generateRedisKeys(districts);
        spreadDistrictsTree(new AtomicInteger(0), "0", districts, saveList);
        this.remove(new QueryWrapper<>());
        this.saveBatch(saveList);
        log.info("load district {} data to redis success", saveList.size());
    }

    /**
     * 生成redis key
     *
     * @param districts
     * @return
     */
    private List<Pair<String, DistrictDto>> generateRedisKeys(List<DistrictDto> districts) {
        List<Pair<String, DistrictDto>> keys = new ArrayList<>(44000);
        for (DistrictDto district : districts) {
            DistrictLevelEnum levelEnum = DistrictLevelEnum.of(district.getLevel());
            switch (levelEnum) {
                case PROVINCE:
                    keys.add(new Pair<>("key_prefix:" + district.getName() + ":::", district));
                    break;
                case CITY:
                    keys.add(new Pair<>("key_prefix::" + district.getName() + "::", district));
                    break;
                case STRICT:
                    keys.add(new Pair<>("key_prefix:::" + district.getName() + ":", district));
                    break;
                case DISTRICT:
                    keys.add(new Pair<>("key_prefix:::" + district.getName(), district));
                default:
                    break;
            }
            if (!CollectionUtils.isEmpty(district.getDistricts())) {
                keys.addAll(generateRedisKeys(district.getDistricts()));
            }
        }

        return keys;
    }

    @Override
    public List<District> listCity(String cid) {

        List<District> list = super.list(new LambdaQueryWrapper<District>().eq(District::getPid, cid));

        Map<String, List<District>> listMap = findAllList(list).stream().collect(Collectors.groupingBy(District::getPid));
        buildTree(list, listMap);
        return list;
    }

    private void buildTree(List<District> list, Map<String, List<District>> disMap) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }


        for (District district : list) {
            district.setDistricts(disMap.get(district.getId()));
            buildTree(district.getDistricts(), disMap);
        }
    }

    private List<District> findAllList(List<District> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<District> subLists = super.list(new LambdaQueryWrapper<District>().in(District::getPid, list.stream().map(District::getId).collect(Collectors.toList())));

        if (!CollectionUtils.isEmpty(subLists)) {
            list.addAll(findAllList(subLists));
        }
        return list;
    }

    private void spreadDistrictsTree(AtomicInteger id, String pid, List<DistrictDto> districts, List<District> ans) {
        District itr;
        for (DistrictDto district : districts) {
            itr = District.builder().id(String.valueOf(id.incrementAndGet())).pid(pid).citycode(district.getCitycode().toString()).adcode(district.getAdcode()).name(district.getName()).center(district.getCenter()).level(district.getLevel()).build();
            ans.add(itr);
            if (!CollectionUtils.isEmpty(district.getDistricts())) {
                spreadDistrictsTree(id, itr.getId(), district.getDistricts(), ans);
            }
        }
    }
}