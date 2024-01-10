package top.zoowayss.web.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 * Author: <a href="https://github.com/zooways">zooways</a>
 * Date: 2023-04-25
 */
@Slf4j
public class JsonUtils {
    private static ObjectMapper jsonMapper = new ObjectMapper();

    public static <T> T toObj(String str, Class<T> clz) {
        try {
            return jsonMapper.readValue(str, clz);
        } catch (JsonProcessingException e) {
            log.error("{}", str);
            throw new UnsupportedOperationException(e);
        }
    }

    public static String toStr(Object t) {
        try {
            return jsonMapper.writeValueAsString(t);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> T toObj(String str, TypeReference<T> type) {
        try {
            return new ObjectMapper().readValue(str, type);
        } catch (JsonProcessingException e) {
            log.error("{}", str);
            throw new RuntimeException(e);
        }
    }
}
