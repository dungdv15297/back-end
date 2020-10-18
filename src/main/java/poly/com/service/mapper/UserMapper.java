package poly.com.service.mapper;

import poly.com.domain.AccountDetail;
import poly.com.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface UserMapper extends EntityMapper<UserDTO, AccountDetail>{

    default AccountDetail fromId(String id){
        if(id == null){
            return null;
        }
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setId(id);
        return accountDetail;
    }
}
