package poly.com;

import poly.com.config.common.MD5Library;
import poly.com.domain.Account;
import poly.com.domain.AccountDetail;
import poly.com.repository.AccountRepository;
import poly.com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.UUID;

@SpringBootApplication
public class MainApplication {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initAccount(){
        String accountId = UUID.randomUUID().toString();
        String accountDetailId = UUID.randomUUID().toString();
        Account account = new Account();
        account.setId("fcbf8ebc-498b-4416-a854-597697f1ae70");
        account.setUsername("admin");
        account.setPassword(MD5Library.md5("admin123"));
        account.setStatus(1);
        account.setCreatedDate(Instant.now());
        account.setCreatedBy("admin");
        account.setLastModifiedDate(Instant.now());
        account.setLastModifiedBy("admin");
        account.setAccountDetailId("7cec520b-c922-4f4b-83c9-e5129a2ad989");
        account.setRole(1);

        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setId("7cec520b-c922-4f4b-83c9-e5129a2ad989");
        accountDetail.setName("Tran Nam Anh");
        accountDetail.setBirthday(Instant.now());
        accountDetail.setGender(1);
        accountDetail.setAddress("Thuong tin - Ha Noi");
        accountDetail.setEmail("rubik6958@gmail.com");
        accountDetail.setPhone("093938371");
        accountDetail.setBalance(1);
        accountDetail.setStatus(1);
        accountDetail.setCreatedDate(Instant.now());
        accountDetail.setCreatedBy("admin");
        accountDetail.setLastModifiedDate(Instant.now());
        accountDetail.setLastModifiedBy("admin");
        userRepository.save(accountDetail);
        accountRepository.save(account);

    }
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
