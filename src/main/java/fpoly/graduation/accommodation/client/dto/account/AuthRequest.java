package fpoly.graduation.accommodation.client.dto.account;

import fpoly.graduation.accommodation.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private Account account;
}
