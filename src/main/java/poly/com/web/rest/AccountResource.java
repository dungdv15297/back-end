package poly.com.web.rest;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import poly.com.client.dto.account.*;
import poly.com.client.dto.accountDetail.AccountDetailDto;
import poly.com.config.*;
import poly.com.config.common.*;
import poly.com.config.common.exception.ServiceException;
import poly.com.config.common.util.ResponseUtil;
import poly.com.domain.Account;
import poly.com.domain.AccountDetail;
import poly.com.repository.AccountDetailRepository;
import poly.com.repository.AccountRepository;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
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

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    public AccountResource(
            AccountService accountService,
            JwtUtil jwtUtil
            ){
        this.accountService = accountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/avatar")
    public String getAvatar(@RequestBody String accountId) {
        return accountService.getAvatar(accountId);
    }

    @PostMapping("/update-avatar")
    public void updateAvatar(@RequestBody String avatarBase64, @RequestParam String id) {
        accountService.updateAvatar(avatarBase64, id);
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

    @PostMapping("/update")
    public ResponseEntity<BaseDataResponse> update(@RequestBody UpdateAccountRequest request) {
        Account account = accountRepository.findByUserName(request.getUsername());
        if (account == null) {
            // ERR010: Account not found
            return ResponseEntity.badRequest().body(BaseDataResponse.builder().responseCode("ERR010").build());
        }
        if (!MD5Library.compareMd5(request.getPassword(), account.getPassword())) {
            // Password is invalid
            return ResponseEntity.badRequest().body(BaseDataResponse.builder().responseCode("ERR011").build());
        }
        if (!request.getNewPassword().equals(request.getConfirm())) {
            // New password and confirm password not be compared
            return ResponseEntity.badRequest().body(BaseDataResponse.builder().responseCode("ERR012").build());
        }

        account.setPassword(MD5Library.md5(request.getNewPassword()));
        account.setLastModifiedDate(Instant.now());
        account.setLastModifiedBy(account.getUsername());
        accountRepository.save(account);
        return ResponseEntity.ok(BaseDataResponse.builder().build());
    }

    @GetMapping("/")
    public String welcome(){
        return "Hello every body" + Instant.now();
    }

    @GetMapping("/byToken")
    public ResponseEntity<AccountDetailDto> getAccountDetailByToken() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return ResponseEntity.badRequest().body(AccountDetailDto.builder().errorCode("ERR006").build());
        }
        String username = auth.getName();
        Account account = accountRepository.findByUserName(username);
        if (account == null) {
            return ResponseEntity.badRequest().body(AccountDetailDto.builder().errorCode("ERR007").build());
        }
        AccountDetail accountDetail = accountDetailRepository.findById(account.getId());
        if (accountDetail == null) {
            return ResponseEntity.badRequest().body(AccountDetailDto.builder().errorCode("ERR007").build());
        }
        return ResponseEntity.ok(accountDetail.toDto());
    }

    @PostMapping("/getById")
    public ResponseEntity<AccountDetailDto> getAccountDetailById(@RequestBody String id) throws Exception {
        try {
            AccountDetail accountDetail = accountDetailRepository.findById(id);
            if (accountDetail == null) {
                return ResponseEntity.badRequest().body(AccountDetailDto.builder().errorCode("ERR007").build());
            }
            Account account = accountRepository.findById(id);
            AccountDetailDto result = accountDetail.toDto();
            result.setUsername(account.getUsername());
            result.setUpdatedAc(account.getLastModifiedDate().toString());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Khong the tim thay thong tin tai khoan
            return ResponseEntity.badRequest().body(AccountDetailDto.builder().errorCode("ERR007").build());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest request) throws Exception{
        try {
            UserDetails userDetails = accountService.loadUserByUsername(request.getUsername());
//            if(!MD5Library.compareMd5(request.getPassword(), userDetails.getPassword())){
//                throw new UsernameNotFoundException("PasswordNotFound");
//            }
            SecurityContextHolder.getContext().getAuthentication();
            String token = jwtUtil.generateToken((CustomUserDetails) userDetails);
            return ResponseEntity.ok(AuthResponse.builder().jwt(token).build());
        }
        catch (Exception e){
            throw new Exception("inavalid username/password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) throws Exception {
        try {
            String fullname = request.getFullname().trim();
            String phone = request.getPhone().trim().replace(" ", "");
            String username = request.getUsername().trim().replace(" ","");
            String password = request.getPassword();
            String rePassword = request.getRePassword();
            if (fullname.length() == 0
                    || !phone.matches("\\+?(0|84)\\d{9}")
                    || username.length() < 4
                    || password.length() < 6
                    || rePassword.length() < 6
                    || !password.equals(rePassword)) {
                // Nội dung ERR004: Thông tin đã nhập không hợp lệ
                return ResponseEntity.badRequest().body(AuthResponse.builder().errorCode("ERR004").build());
            }

            Account checkAcc = accountRepository.findByUserName(username);
            // Check xem user có tồn tại trong DB hay không, nếu đã tồn tại, return exception ERR05
            // Nội dung ERR05: Username đã tồn tại trong hệ thống
            if (checkAcc != null) {
                return ResponseEntity.badRequest().body(AuthResponse.builder().errorCode("ERR005").build());
            }
            String id = UUID.randomUUID().toString();
            Account account = new Account();
            AccountDetail accountDetail = new AccountDetail();
            account.setId(id);
            account.setUsername(username);
            account.setPassword(MD5Library.md5(password));
            if(request.getRole() != null){
                account.setRole(Role.Admin);
            } else {
                account.setRole(Role.User);
            }
            account.setCreatedDate(Instant.now());
            account.setCreatedBy(username);
            account.setLastModifiedBy(username);
            account.setLastModifiedDate(Instant.now());
            account.setStatus(AccountStatus.NotVerified);

            accountDetail.setId(id);
            accountDetail.setName(fullname);
            accountDetail.setPhone(phone);
            accountDetail.setBirthday(Instant.now());
            accountDetail.setCreatedDate(Instant.now());
            accountDetail.setCreatedBy(username);
            accountDetail.setLastModifiedBy(username);
            accountDetail.setLastModifiedDate(Instant.now());
            accountDetail.setStatus(Status.Active);
            accountDetail.setBalance(0);
            accountDetail.setGender(0);
            accountDetailRepository.save(accountDetail);
            accountRepository.save(account);

            // Sau khi thêm tài khoản và chi tiết tài khoản, tìm tài khoản
            // Trả về client token tương ứng
            UserDetails userAfterCreated = accountService.loadUserByUsername(username);
            SecurityContextHolder.getContext().getAuthentication();
            String token = jwtUtil.generateToken((CustomUserDetails) userAfterCreated);
            return ResponseEntity.ok(AuthResponse.builder().jwt(token).build());
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @PostMapping("/check-payment")
    public Boolean checkPayment() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUserName(username);
        if (account == null) {
            return false;
        }
        AccountDetail accountDetail = accountDetailRepository.findById(account.getId());
        if (accountDetail == null) {
            return false;
        }
        Integer balance = accountDetail.getBalance();
        if (balance >= 10000) {
            return true;
        }
        return false;
    }
}
