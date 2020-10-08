package poly.com.config.common.security;

import org.springframework.security.core.Authentication;

/**
 * App authorizer
 * Author: DungDV
 */
public interface AppAuthorizer {
    boolean authorize(Authentication authentication, Object callerObj);
}
