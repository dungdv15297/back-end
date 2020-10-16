package poly.com.client.dto.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAccountRequest {
    String username;
    String password;
    String newPassword;
    String confirm;
}
