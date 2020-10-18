package poly.com.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;
import poly.com.domain.Room;
import poly.com.service.dto.RoomDTO;

@Mapper(componentModel = "spring",uses = {})
public interface RoomMapper extends EntityMapper<RoomDTO, Room>{

    default Room fromId(String id){
        if(id == null){
            return null;
        }
        if(StringUtils.isEmpty(id)){
            return null;
        }
        Room room = new Room();
        room.setId(id);
        return room;
    }
}
