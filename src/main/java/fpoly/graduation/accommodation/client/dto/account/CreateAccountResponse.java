package fpoly.graduation.accommodation.client.dto.account;

import fpoly.graduation.accommodation.service.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountResponse {
    private AccountDTO account;
}
