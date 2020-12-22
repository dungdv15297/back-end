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
import poly.com.domain.Payment;
import poly.com.repository.AccountDetailRepository;
import poly.com.repository.AccountRepository;
import poly.com.repository.PaymentRepository;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/account/detail")
public class AccountDetailResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping("/updateByUser")
    public String updateDetail(@RequestBody AccountDetailDto param) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUserName(username);
        Payment payment = new Payment();
        AccountDetail oldDetail = accountDetailRepository.findById(account.getId());
        Integer amount = param.getBalance() - oldDetail.getBalance();
        oldDetail.setGender(param.getGender());
        oldDetail.setName(param.getName());
        oldDetail.setBirthday(Instant.parse(param.getBirthday()));
        oldDetail.setEmail(param.getEmail());
        oldDetail.setAddress(param.getAddress());
        oldDetail.setLastModifiedBy(username);
        oldDetail.setLastModifiedDate(Instant.now());
        oldDetail.setBalance(param.getBalance());
        accountDetailRepository.save(oldDetail);
        payment.setId(UUID.randomUUID().toString());
        payment.setAccount(account);
        payment.setPaymentInfor('+' + amount.toString());
        payment.setStatus(1);
        payment.setCreatedBy(account.getUsername());
        payment.setCreatedDate(Instant.now());
        payment.setLastModifiedBy(account.getUsername());
        payment.setLastModifiedDate(Instant.now());
        paymentRepository.save(payment);
        return oldDetail.getId();
    }
}
