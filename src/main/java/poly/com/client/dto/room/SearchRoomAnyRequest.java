package poly.com.client.dto.room;

import lombok.Data;

@Data
public class SearchRoomAnyRequest {
    private Integer type;
    private Integer province;
    private Integer district;
    private Integer street;
    private Integer acreage;
    private Integer price;
    private Integer page;
    private Integer size;
    private String accountId;
}