package poly.com.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardInfor {
    Integer uptop;
    Integer notUptop;
    Integer monthUptop;
    Integer monthNotUptop;
    Integer month;
    Integer year;
}
