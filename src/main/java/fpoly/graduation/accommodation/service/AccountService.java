package fpoly.graduation.accommodation.service;

import com.fis.egp.common.client.rest.dto.ValidationErrorResponse;
import com.fis.egp.common.config.ValidationError;
import com.fis.egp.common.exception.ServiceException;
import com.fis.egp.common.security.SecurityUtils;
import com.fis.egp.common.util.ServiceExceptionBuilder;
import com.fis.egp.common.util.ServiceUtil;
import fpoly.graduation.accommodation.client.dto.account.CreateAccountRequest;
import fpoly.graduation.accommodation.client.dto.account.CreateAccountResponse;
import fpoly.graduation.accommodation.domain.Account;
import fpoly.graduation.accommodation.domain.Users;
import fpoly.graduation.accommodation.repository.AccountRepository;
import fpoly.graduation.accommodation.repository.UserRepository;
import fpoly.graduation.accommodation.service.dto.AccountDTO;
import fpoly.graduation.accommodation.service.dto.UserDTO;
import fpoly.graduation.accommodation.service.mapper.AccountMapper;
import fpoly.graduation.accommodation.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional(rollbackFor = {
        ServiceException.class,
        Exception.class
})
public class AccountService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public AccountService(
            AccountRepository accountRepository,
            AccountMapper accountMapper,
            UserRepository userRepository,
            UserMapper userMapper
    ){
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.userMapper= userMapper;
    }

    public CreateAccountResponse create(CreateAccountRequest request) throws ServiceException, Exception{
        try {
            if(request == null){
                ServiceUtil.generateEmptyPayloadError();
            }
            if(request.getAccount() == null){
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("account", ValidationError.NotNull))
                        .build();
            }
            Optional<Account> accountSearch = accountRepository.findUserName(request.getAccount().getUsername());
            if(accountSearch.isPresent()){
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("account",ValidationError.Duplicate))
                        .build();
            }

            AccountDTO dto = request.getAccount();
            Account account = accountMapper.toEntity(dto);

            account.setUsername(request.getAccount().getUsername());
            account.setPassword(request.getAccount().getPassword());
            account.setStatus(request.getAccount().getStatus());
            account.setVerified(request.getAccount().getVerified());
            account.setCreatedDate(Instant.now());
            account.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
            account.setLastModifiedDate(Instant.now());
            account.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());


            UserDTO userDTO = request.getAccount().getUser();
            Users users = userMapper.toEntity(userDTO);
            users.setName(request.getAccount().getUser().getName());
            users.setBirthday(request.getAccount().getUser().getBirthday());
            users.setGender(request.getAccount().getUser().getGender());
            users.setAddress(request.getAccount().getUser().getAddress());
            users.setEmail(request.getAccount().getUser().getEmail());
            users.setPhone(request.getAccount().getUser().getPhone());
            users.setBalance(request.getAccount().getUser().getBalance());
            users.setVerified(request.getAccount().getUser().getVerified());
            users.setStatus(request.getAccount().getUser().getStatus());
            users.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
            users.setCreatedDate(Instant.now());
            users.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
            users.setLastModifiedDate(Instant.now());

            userRepository.save(users);
            CreateAccountResponse response = new CreateAccountResponse();
            accountRepository.save(account);
            response.setAccount(accountMapper.toDto(account));

            return response;
        }
        catch (ServiceException e){
            throw e;
        }
        catch (Exception e){
            throw e;
        }

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Account account = accountRepository.findByUserName(username);
//        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), new ArrayList<>());
        try {
            Account account = accountRepository.findByUserName(username);
            return new User(account.getUsername(),account.getPassword(),new ArrayList<>());
        }
        catch (Exception e){
            throw e;
        }
    }
}
