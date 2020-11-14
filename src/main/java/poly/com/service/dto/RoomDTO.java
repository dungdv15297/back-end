package poly.com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.time.Instant;

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

    private Float longtitude;

    private Float latitude;

    private Integer status;

    private String accountId;

    private Integer wardId;

    private AccountDTO account;

    private Integer upTopStatus;

    private List<String> pictures;

    private String title;

    private Integer type;

    private Integer typeOfRoom;

}
