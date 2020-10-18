package poly.com.service.mapper;

import org.mapstruct.Mapper;
import poly.com.domain.AcreageRange;
import poly.com.service.dto.AcreageRangeDTO;

@Mapper(componentModel = "spring",uses = {})
public interface AcreageRageMapper extends EntityMapper<AcreageRangeDTO, AcreageRange>{

    default AcreageRange fromId(Integer id){
        if(id == null){
            return null;
        }
        AcreageRange acreageRange = new AcreageRange();
        acreageRange.setId(id);
        return acreageRange;
    }
}
