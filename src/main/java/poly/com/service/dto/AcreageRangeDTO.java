package poly.com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcreageRangeDTO implements Serializable {

    private Integer id;

    private Integer min;

    private Integer max;

    private Integer status;

    private String description;
}
