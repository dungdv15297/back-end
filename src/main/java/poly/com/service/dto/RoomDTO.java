package poly.com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private String id;

    private String address;

    private String description;

    private Integer priceMin;

    private Integer priceMax;

    private Integer acreageMin;

    private Integer acreageMax;

    private Integer longtitude;

    private Integer latitude;

    private Integer status;

    private Integer priceRangeId;

    private Integer acreageRangId;

    private Integer accountId;

    private Integer streetId;

    private PriceRageDTO priceRage;

    private AcreageRangeDTO acreageRange;

    private AccountDTO account;

    private StreetDTO street;
}
