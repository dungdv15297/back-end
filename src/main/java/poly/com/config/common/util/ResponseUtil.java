package poly.com.config.common.util;

import poly.com.config.common.exception.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface ResponseUtil {
    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }

    /**
     * Wrap the optional into a {@link org.springframework.http.ResponseEntity} with an {@link org.springframework.http.HttpStatus#OK} status with the headers, or if it's
     * empty, throws a {@link org.springframework.web.server.ResponseStatusException} with status {@link org.springframework.http.HttpStatus#NOT_FOUND}.
     *
     * @param <X>           type of the response
     * @param maybeResponse response to return if present
     * @param header        headers to be added to the response
     * @return response containing {@code maybeResponse} if present
     * 
     */
    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return maybeResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    static ResponseEntity wrap(Object object){
        return ResponseEntity.ok(object);
    }
    static ResponseEntity generateErrorResponse(Exception e){
        return ResponseEntity.ok(e);
    }
    static ResponseEntity generateErrorResponse(ServiceException e){
        return ResponseEntity.ok(e);
    }
}
