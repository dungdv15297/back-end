package poly.com.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProvinceDetail {
    List<String> labels;
    List<Integer> uptop;
    List<Integer> unuptop;
}
