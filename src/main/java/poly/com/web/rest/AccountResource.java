package poly.com.web.rest;

//import com.fis.egp.common.exception.ServiceException;
import poly.com.client.dto.account.AuthRequest;
import poly.com.client.dto.account.AuthResponse;
import poly.com.client.dto.account.CreateAccountRequest;
import poly.com.client.dto.account.CreateAccountResponse;
import poly.com.config.CustomUserDetails;
import poly.com.config.JwtUtil;
import poly.com.config.common.BaseDataRequest;
import poly.com.config.common.BaseDataResponse;
import poly.com.config.common.MD5Library;
import poly.com.config.common.exception.ServiceException;
import poly.com.config.common.util.ResponseUtil;
import poly.com.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/account")
public class AccountResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private AccountService accountService;

    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AccountResource(
            AccountService accountService,
            JwtUtil jwtUtil
            ){
        this.accountService = accountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/add-account")
    public ResponseEntity<BaseDataResponse<CreateAccountResponse>> create(
            @RequestBody BaseDataRequest<CreateAccountRequest> request
            ){
        try {
            // UUID id = UUID.randomUUID();
            CreateAccountResponse response = accountService.create(request.getBody());
            return ResponseUtil.wrap(response);
//            return ResponseUtil.wrap(response);
        } catch (Exception e){
            return ResponseUtil.generateErrorResponse(e);
        }
        catch (ServiceException e){
            return ResponseUtil.generateErrorResponse(e);
        }
    }
    @GetMapping("/")
    public String welcome(){
        return "Hello every body";
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest request) throws Exception{
        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
            UserDetails userDetails = accountService.loadUserByUsername(request.getUsername());
            if(!MD5Library.compareMd5(request.getPassword(), userDetails.getPassword())){
                throw new UsernameNotFoundException("PasswordNotFound");
            }
            SecurityContextHolder.getContext().getAuthentication();
            String token = jwtUtil.generateToken((CustomUserDetails) userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        }
        catch (Exception e){
            throw new Exception("inavalid username/password");
        }
    }
}
