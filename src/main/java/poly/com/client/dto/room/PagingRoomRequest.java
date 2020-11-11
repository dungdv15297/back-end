package poly.com.client.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import poly.com.service.dto.RoomDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingRoomRequest {
    private FilterRoomRequest room;
    private Integer pageNumber;
    private Integer pageSize;
    private String orderBy;
}
