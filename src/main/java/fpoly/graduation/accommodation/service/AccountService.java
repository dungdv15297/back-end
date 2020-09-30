package fpoly.graduation.accommodation.service;

import com.fis.egp.common.client.rest.dto.ValidationErrorResponse;
import com.fis.egp.common.config.ValidationError;
import com.fis.egp.common.exception.ServiceException;
import com.fis.egp.common.util.ServiceExceptionBuilder;
import com.fis.egp.common.util.ServiceUtil;
import fpoly.graduation.accommodation.client.dto.account.CreateAccountRequest;
import fpoly.graduation.accommodation.client.dto.account.CreateAccountResponse;
import fpoly.graduation.accommodation.domain.Account;
import fpoly.graduation.accommodation.repository.AccountRepository;
import fpoly.graduation.accommodation.service.dto.AccountDTO;
import fpoly.graduation.accommodation.service.mapper.AccountMapper;
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

    public AccountService(
            AccountRepository accountRepository,
            AccountMapper accountMapper
    ){
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
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
            AccountDTO dto = request.getAccount();
            Account account = accountMapper.toEntity(dto);

            account.setUsername(request.getAccount().getUsername());
            account.setPassword(request.getAccount().getPassword());
            account.setStatus(request.getAccount().getStatus());
            account.setVerified(request.getAccount().getVerified());
            account.setCreatedDate(Instant.now());
            account.setCreatedBy("admin");
            account.setLastModifiedDate(Instant.now());
            account.setLastModifiedBy("admin");

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
