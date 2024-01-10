package top.zoowayss.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pair<T, U> {
    private T first;
    private U second;
}
