package poly.com.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Uptop {
    private Boolean accept;
    private String time;
}