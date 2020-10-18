package poly.com.service;

import poly.com.client.dto.account.CreateAccountRequest;
import poly.com.client.dto.account.CreateAccountResponse;
import poly.com.config.CustomUserDetails;
import poly.com.config.common.ValidationErrorResponse;
import poly.com.config.common.exception.ServiceException;
import poly.com.config.common.security.SecurityUtils;
import poly.com.config.common.util.ServiceExceptionBuilder;
import poly.com.config.common.validationError.ValidationError;
import poly.com.domain.Account;
import poly.com.domain.AccountDetail;
import poly.com.repository.AccountDetailRepository;
import poly.com.repository.AccountRepository;
import poly.com.service.dto.AccountDTO;
import poly.com.service.dto.UserDTO;
import poly.com.service.mapper.AccountMapper;
import poly.com.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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

    private final AccountDetailRepository accountDetailRepository;

    private final UserMapper userMapper;

    public AccountService(
            AccountRepository accountRepository,
            AccountMapper accountMapper,
            AccountDetailRepository accountDetailRepository,
            UserMapper userMapper
    ){
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.accountDetailRepository = accountDetailRepository;
        this.userMapper= userMapper;
    }

    public CreateAccountResponse create(CreateAccountRequest request) throws ServiceException, Exception {
        try {
//            if(request == null){
//                ServiceUtil.generateEmptyPayloadError();
//            }
//            if(request.getAccount() == null){
//                throw ServiceExceptionBuilder.newBuilder()
//                        .addError(new ValidationErrorResponse("account", ValidationError.NotNull))
//                        .build();
//            }
            Optional<Account> accountSearch = accountRepository.findUserName(request.getAccount().getUsername());
            if(accountSearch.isPresent()){
//                throw ServiceExceptionBuilder.newBuilder()
//                        .addError(new ValidationErrorResponse("account", ValidationError.Duplicate))
//                        .build();
//               throw com.fis.egp.common.util.ServiceExceptionBuilder.newBuilder()

                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("account", ValidationError.Duplicate))
                        .build();

            }

            AccountDTO dto = request.getAccount();
            Account account = accountMapper.toEntity(dto);

            account.setUsername(request.getAccount().getUsername());
            account.setPassword(request.getAccount().getPassword());
            account.setStatus(request.getAccount().getStatus());
            account.setCreatedDate(Instant.now());
            account.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
            account.setLastModifiedDate(Instant.now());
            account.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());


            UserDTO userDTO = request.getAccount().getUser();
            AccountDetail accountDetail = userMapper.toEntity(userDTO);
            accountDetail.setName(request.getAccount().getUser().getName());
            accountDetail.setBirthday(request.getAccount().getUser().getBirthday());
            accountDetail.setGender(request.getAccount().getUser().getGender());
            accountDetail.setAddress(request.getAccount().getUser().getAddress());
            accountDetail.setEmail(request.getAccount().getUser().getEmail());
            accountDetail.setPhone(request.getAccount().getUser().getPhone());
            accountDetail.setBalance(request.getAccount().getUser().getBalance());
            accountDetail.setStatus(request.getAccount().getUser().getStatus());
            accountDetail.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
            accountDetail.setCreatedDate(Instant.now());
            accountDetail.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
            accountDetail.setLastModifiedDate(Instant.now());

            accountDetailRepository.save(accountDetail);
            CreateAccountResponse response = new CreateAccountResponse();
            accountRepository.save(account);
            response.setAccount(accountMapper.toDto(account));

            return response;
        } catch (Exception e){
            throw e;
        }

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Account account = accountRepository.findByUserName(username);
            if(account == null){
                throw new UsernameNotFoundException(username);
            }
            System.out.println(account.toString());
            return new CustomUserDetails(account);
        }
        catch (Exception e){
            throw e;
        }
    }

}
