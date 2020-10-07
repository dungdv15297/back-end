package fpoly.graduation.accommodation.config.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {
    private String key;
    private String errorCode;
    private String errorMessage;
    private List<KeyValue> params;
    public ValidationErrorResponse(String  key,String errorCode){
        this.key=key;
        this.errorCode=errorCode;
    }
}
