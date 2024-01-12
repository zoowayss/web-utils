package top.zoowayss.web.controller.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zoowayss.web.entity.District;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class AreaVo {

    private String id;
    private String name;
    private String center;
    private String level;
    private AreaVo districts;

    public AreaVo(District district) {
        this.id = district.getId();
        this.name=district.getName();
        this.center=district.getCenter();
        this.level=district.getLevel();
    }
}
