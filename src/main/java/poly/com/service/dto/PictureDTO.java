package poly.com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureDTO {
    private Integer id;

    private RoomDTO room;

    private String src;

    private Integer status;

    private String name;

    private  String type;


}
