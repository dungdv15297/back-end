package poly.com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreetDTO implements Serializable {

    private Integer id;

    private String name;

    private Integer status;

    private Integer districtId;

    private String prefix;

    private Integer provinceId;

    private DistrictDto district;
}
