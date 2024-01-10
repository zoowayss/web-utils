package top.zoowayss.web.utils;

import java.util.UUID;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */

public class UUIDUtils {

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generate(int length) {
        return generate().substring(0, length);
    }
}
