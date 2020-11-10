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
public class PriceRageDTO implements Serializable {

    private Integer id;

    private Integer min;

    private Integer max;

    private String description;

    private Integer status;
}
