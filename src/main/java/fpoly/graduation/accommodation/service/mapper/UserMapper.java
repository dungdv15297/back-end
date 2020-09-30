package fpoly.graduation.accommodation.service.mapper;

import fpoly.graduation.accommodation.domain.Users;
import fpoly.graduation.accommodation.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface UserMapper extends EntityMapper<UserDTO, Users>{

    default Users fromId(Integer id){
        if(id == null){
            return null;
        }
        Users users = new Users();
        users.setId(id);
        return users;
    }
}
