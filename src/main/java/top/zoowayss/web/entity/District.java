package top.zoowayss.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_district")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class District {

    public static final String LEVEL_COUNTRY = "country";
    public static final String LEVEL_PROVINCE = "province";
    public static final String LEVEL_CITY = "city";
    public static final String LEVEL_STREET = "street";
    public static final String LEVEL_DISTRICT = "district";

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "citycode")
    private Object citycode;

    @TableField(value = "adcode")
    private String adcode;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "center")
    private String center;

    @TableField(value = "`level`")
    private String level;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value = "pid")
    private String pid;


    @TableField(exist = false)
    private List<District> Districts;

}