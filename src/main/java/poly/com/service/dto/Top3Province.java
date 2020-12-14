package poly.com.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Top3Province {
    private Integer provinceId;
    private String provinceName;
    private List<String> labels;
    private List<Integer> data;
}
