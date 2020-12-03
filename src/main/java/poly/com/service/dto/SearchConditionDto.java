package poly.com.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchConditionDto {
    String accountId;
    Integer provinceId;
    Integer districtId;
    Integer typeOfRoom;
    Integer priceMin;
    Integer priceMax;
    Integer acreageMin;
    Integer acreageMax;
}
