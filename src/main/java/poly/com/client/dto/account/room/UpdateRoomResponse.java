package poly.com.client.dto.account.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.com.service.dto.RoomDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomResponse {
    private RoomDTO room;
}
