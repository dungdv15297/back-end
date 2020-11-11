package poly.com.client.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.com.service.dto.AccountDTO;
import poly.com.service.dto.DistrictDto;
import poly.com.service.dto.StreetDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterRoomRequest {
    private String id;

    private String address;

    private String description;

    private Integer priceMin;

    private Integer priceMax;

    private Integer acreageMin;

    private Integer acreageMax;

    private Float longtitude;

    private Float latitude;

    private Integer status;

    private String accountId;

    private Integer streetId;

    private AccountDTO account;

    private StreetDTO street;

    private Integer upTopStatus;

    private List<String> pictures;

    private String title;

    private Integer typeOfRoom;

    private DistrictDto district;
}
