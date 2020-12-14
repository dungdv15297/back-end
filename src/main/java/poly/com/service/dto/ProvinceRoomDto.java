package poly.com.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProvinceRoomDto {
    String provinceId;
    String provinceName;
    Integer uptop;
    Integer other;
}
