package poly.com.client.dto.account;

import lombok.Data;

@Data
public class MasterTableRequest {
    private Integer id;
    private Integer min;
    private Integer max;
}