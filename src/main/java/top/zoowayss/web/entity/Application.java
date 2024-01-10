package top.zoowayss.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_application")
public class Application {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String appId;

    private String secret;

    private String userId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Boolean enabled;

}
