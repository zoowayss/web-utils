package top.zoowayss.web.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */

@Getter
@AllArgsConstructor
public enum DistrictLevelEnum {
    COUNTRY("country"),
    PROVINCE("province"),
    CITY("city"),
    STRICT("street"),
    DISTRICT("district");
    private String level;

    private static Map<String, DistrictLevelEnum> cache;

    static {
        cache = Arrays.stream(DistrictLevelEnum.values()).collect(Collectors.toMap(DistrictLevelEnum::getLevel, Function.identity()));
    }

    public static DistrictLevelEnum of(String level) {
        DistrictLevelEnum typeEnum = cache.get(level);
        if (typeEnum == null) {
            throw new RuntimeException("DistrictLevelEnum not found, level: " + level);
        }
        return typeEnum;
    }
}
