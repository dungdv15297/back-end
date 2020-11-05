package poly.com.service.mapper;

import org.mapstruct.Mapper;
import poly.com.domain.Picture;
import poly.com.service.dto.PictureDTO;

@Mapper(componentModel = "spring", uses = {})
public interface PictureMapper extends EntityMapper<PictureDTO, Picture> {

    default Picture fromId(Integer id ){
        if(id == null){
            return null;
        }
        else{
            Picture picture = new Picture();
            picture.setId(id);
            return picture;
        }
    }
}
