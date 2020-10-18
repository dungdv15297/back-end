package poly.com.service.mapper;

import poly.com.domain.Account;
import poly.com.service.dto.AccountDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface AccountMapper extends EntityMapper<AccountDTO, Account>{

    default  Account fromId(String id){
        if(id == null){
            return null;
        }
        Account account = new Account();
        account.setId(id);
        return account;
    }
}
