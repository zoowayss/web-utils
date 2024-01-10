package top.zoowayss.web.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDto {
    private Integer id;
    private Integer pid;
    private Object citycode;
    private String adcode;
    private String name;
    private String center;
    private String level;

    private List<DistrictDto> districts;
}
