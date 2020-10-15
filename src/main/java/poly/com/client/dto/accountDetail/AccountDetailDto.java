package poly.com.client.dto.accountDetail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDetailDto {
    private String id;
    private String name;
    private String birthday;
    private Integer gender;
    private String address;
    private String email;
    private String phone;
    private Integer balance;
    private Integer status;
    private String updated;
    private String username;
    private String updatedAc;
    private String errorCode;
}
