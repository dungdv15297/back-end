package poly.com.client.dto.account.room;

import lombok.Data;
import poly.com.service.dto.RoomDTO;

@Data
public class CreateRoomRequest {
    private RoomDTO room;
}
