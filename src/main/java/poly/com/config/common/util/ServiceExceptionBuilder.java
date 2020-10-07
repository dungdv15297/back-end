package poly.com.config.common.util;

import poly.com.config.common.ValidationErrorResponse;
import poly.com.config.common.exception.ServiceException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ServiceExceptionBuilder {

    public static Builder newBuilder(){
          return  new Builder();
    }
    public static class Builder{
        public List<ValidationErrorResponse> errors;
        public Builder(){}
        public Builder addError(ValidationErrorResponse errorResponse){
            return new Builder().addError(errorResponse);
        }
        public ServiceException build() throws ServiceException {
        throw new ServiceException();
        }
    }
}
