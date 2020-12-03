package poly.com.client.dto.account;

import lombok.Builder;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
@Builder
public class RegisterRequest {
    String fullname;
    String phone;
    String username;
    String password;
    String rePassword;
    Integer role;
}
