package poly.com.client.dto.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    String fullname;
    String phone;
    String username;
    String password;
    String rePassword;
}
