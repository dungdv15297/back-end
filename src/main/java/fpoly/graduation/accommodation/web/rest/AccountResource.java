package fpoly.graduation.accommodation.web.rest;

//import com.fis.egp.common.exception.ServiceException;
import fpoly.graduation.accommodation.client.dto.account.AuthRequest;
import fpoly.graduation.accommodation.client.dto.account.AuthResponse;
import fpoly.graduation.accommodation.client.dto.account.CreateAccountRequest;
import fpoly.graduation.accommodation.client.dto.account.CreateAccountResponse;
import fpoly.graduation.accommodation.config.CustomUserDetails;
import fpoly.graduation.accommodation.config.JwtUtil;
import fpoly.graduation.accommodation.config.common.BaseDataRequest;
import fpoly.graduation.accommodation.config.common.BaseDataResponse;
import fpoly.graduation.accommodation.config.common.exception.ServiceException;
import fpoly.graduation.accommodation.config.common.util.ResponseUtil;
import fpoly.graduation.accommodation.domain.Account;
import fpoly.graduation.accommodation.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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
            if(!request.getPassword().equalsIgnoreCase(userDetails.getPassword())){
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
