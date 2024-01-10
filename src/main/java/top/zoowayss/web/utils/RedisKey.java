package top.zoowayss.web.utils;

/**
 * @author zhongzb create on 2021/06/10
 */
public class RedisKey {
    private static final String BASE_KEY = "web-utils:";

    public static String getKey(String key, Object... objects) {
        return BASE_KEY + String.format(key, objects);
    }

}
