package top.zoowayss.web.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.zoowayss.web.domain.Pair;
import top.zoowayss.web.entity.District;

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

    public String createKey(District  district) {
        DistrictLevelEnum levelEnum =DistrictLevelEnum.of(district.getLevel());
        return switch (levelEnum) {
            case PROVINCE -> "key_prefix:" + district.getName() + ":::";
            case CITY -> "key_prefix::" + district.getName() + "::";
            case STRICT -> "key_prefix:::" + district.getName() + ":";
            case DISTRICT -> "key_prefix:::" + district.getName();
            default -> throw new RuntimeException("level Enum not support");
        };
    }
}
