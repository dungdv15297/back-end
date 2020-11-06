package poly.com.client.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.com.service.dto.RoomDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListRoomResponse {
    private List<RoomDTO> rooms;
}
