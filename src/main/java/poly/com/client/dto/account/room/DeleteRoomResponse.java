package poly.com.client.dto.account.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.com.service.dto.RoomDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRoomResponse {
    private List<RoomDTO> rooms;
}
