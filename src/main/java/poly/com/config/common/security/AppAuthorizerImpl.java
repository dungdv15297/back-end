package poly.com.config.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import poly.com.repository.AccountRepository;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

@Service("appAuthorizer")
public class AppAuthorizerImpl implements AppAuthorizer {
    private final Logger logger = LoggerFactory.getLogger(AppAuthorizerImpl.class);

    private AccountRepository accountRepository;

    @Override
    public boolean authorize(Authentication authentication, Object callerObj) {
        String securedPath = extractSecuredPath(callerObj);
        if (securedPath==null || "".equals(securedPath.trim())) {//login, logout
            return true;
        }
        String menuCode = securedPath.substring(1);//Bỏ dấu "/" ở đầu Path
        boolean isAllow = false;
        try {
            UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) authentication;
            if (user==null){
                return isAllow;
            }
            String userId = (String)user.getPrincipal();
            if (userId==null || "".equals(userId.trim())) {
                return isAllow;
            }
            // Get role of account in DB
            Integer role = accountRepository.getRoleById(userId);
            if (role == 1) {
                isAllow = true;
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
            throw e;
        }
        return isAllow;
    }

    private String extractSecuredPath(Object callerObj) {
        Class<?> clazz = ResolvableType.forClass(callerObj.getClass()).getRawClass();
        Optional<Annotation> annotation = Arrays.asList(clazz.getAnnotations()).stream().filter((ann) -> {
            return ann instanceof PostMapping;
        }).findFirst();
        logger.debug("FOUND CALLER CLASS: {}", ResolvableType.forClass(callerObj.getClass()).getType().getTypeName());
        if (annotation.isPresent()) {
            return ((PostMapping) annotation.get()).value()[0];
        }
        return null;
    }

}
