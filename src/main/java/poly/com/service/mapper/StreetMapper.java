package poly.com.service.mapper;

import org.mapstruct.Mapper;
import poly.com.domain.Street;
import poly.com.service.dto.StreetDTO;

@Mapper(componentModel = "spring",uses = {})
public interface StreetMapper extends EntityMapper<StreetDTO, Street>{

    default Street fromId(Integer id){
        if(id == null){
            return null;
        }
        Street street = new Street();
        street.setId(id);
        return  street;
    }
}
