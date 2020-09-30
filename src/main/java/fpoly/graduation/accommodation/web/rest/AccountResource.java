package fpoly.graduation.accommodation.web.rest;

import com.fis.egp.common.client.rest.dto.BaseDataRequest;
import com.fis.egp.common.client.rest.dto.BaseDataResponse;
import com.fis.egp.common.exception.ServiceException;
import com.fis.egp.common.util.ResponseUtil;
import fpoly.graduation.accommodation.client.dto.account.AuthRequest;
import fpoly.graduation.accommodation.client.dto.account.AuthResponse;
import fpoly.graduation.accommodation.client.dto.account.CreateAccountRequest;
import fpoly.graduation.accommodation.client.dto.account.CreateAccountResponse;
import fpoly.graduation.accommodation.config.JwtUtil;
import fpoly.graduation.accommodation.domain.Account;
import fpoly.graduation.accommodation.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/account")
public class AccountResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private AccountService accountService;

    private JwtUtil jwtUtil;

    private AuthenticationManager authenticationManager;
    public AccountResource(
            AccountService accountService,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager
            ){
        this.accountService = accountService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/add-account")
    public ResponseEntity<BaseDataResponse<CreateAccountResponse>> create(
            @RequestBody BaseDataRequest<CreateAccountRequest> request
            ){
        try {
            CreateAccountResponse response = accountService.create(request.getBody());
            return ResponseUtil.wrap(response);
        }
        catch (ServiceException e){
            return ResponseUtil.generateErrorResponse(e);
        }
        catch (Exception e){
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
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getAccount().getUsername(),request.getAccount().getPassword()));
            UserDetails userDetails = accountService.loadUserByUsername(request.getAccount().getUsername());
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        }
        catch (Exception e){
            throw new Exception("inavalid username/password");
        }
    }
}
