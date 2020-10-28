package poly.com.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import poly.com.client.dto.accountDetail.AccountDetailDto;
import poly.com.domain.Account;
import poly.com.domain.AccountDetail;
import poly.com.repository.AccountDetailRepository;
import poly.com.repository.AccountRepository;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/api/account/detail")
public class AccountDetailResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/updateByUser")
    public String updateDetail(@RequestBody AccountDetailDto param) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUserName(username);

        AccountDetail oldDetail = accountDetailRepository.findById(account.getId());

        oldDetail.setGender(param.getGender());
        oldDetail.setName(param.getName());
        oldDetail.setBirthday(Instant.parse(param.getBirthday()));
        oldDetail.setEmail(param.getEmail());
        oldDetail.setAddress(param.getAddress());
        oldDetail.setLastModifiedBy(username);
        oldDetail.setLastModifiedDate(Instant.now());
        accountDetailRepository.save(oldDetail);
        return oldDetail.getId();
    }
}
