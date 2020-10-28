package poly.com.client.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.com.service.dto.RoomDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListRoomRequest {
    private RoomDTO room;
}
