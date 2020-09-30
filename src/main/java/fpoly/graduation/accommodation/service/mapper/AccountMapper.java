package fpoly.graduation.accommodation.service.mapper;

import fpoly.graduation.accommodation.domain.Account;
import fpoly.graduation.accommodation.service.dto.AccountDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface AccountMapper extends EntityMapper<AccountDTO, Account>{

    default  Account fromId(Integer id){
        if(id == null){
            return null;
        }
        Account account = new Account();
        account.setId(id);
        return account;
    }
}
