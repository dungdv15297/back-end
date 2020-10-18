package poly.com.service.mapper;

import org.mapstruct.Mapper;
import poly.com.domain.PriceRange;
import poly.com.service.dto.PriceRageDTO;

@Mapper(componentModel = "spring",uses = {})
public interface PriceRageMapper extends EntityMapper<PriceRageDTO, PriceRange>{

    default PriceRange fromId(Integer id){
        if(id == null){
            return null;
        }
        PriceRange priceRange = new PriceRange();
        priceRange.setId(id);
        return priceRange;
    }
}
