package poly.com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreetDTO implements Serializable {

    private Integer id;

    private String name;

    private Integer status;

    private Integer districtId;
}
