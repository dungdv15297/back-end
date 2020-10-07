package fpoly.graduation.accommodation.config.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseDataResponse<T> {
    @JsonProperty("responseCode")
    private String responseCode;
    @JsonProperty("responseMessage")
    private String responseMessage;
    @JsonProperty("responseEntityMessages")
    private List<ValidationErrorResponse> responseEntityMessages;
    @JsonProperty("body")
    private T body;
}
