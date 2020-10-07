package poly.com.config.common.exception;

import poly.com.config.common.ValidationErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

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
