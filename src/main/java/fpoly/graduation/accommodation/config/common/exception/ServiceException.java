package fpoly.graduation.accommodation.config.common.exception;

import fpoly.graduation.accommodation.config.common.ValidationErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class ServiceException extends Throwable {
    private List<ValidationErrorResponse> errors;

    public ServiceException(){
    }
    public ServiceException(String message){

    }
}
