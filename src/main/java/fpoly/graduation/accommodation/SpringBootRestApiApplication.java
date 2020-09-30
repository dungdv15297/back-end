package fpoly.graduation.accommodation;

import fpoly.graduation.accommodation.domain.Account;
import fpoly.graduation.accommodation.domain.Users;
import fpoly.graduation.accommodation.repository.AccountRepository;
import fpoly.graduation.accommodation.repository.UserRepository;
import fpoly.graduation.accommodation.service.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringBootRestApiApplication {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initAccount(){

        Account account = new Account();
        account.setId(1);
        account.setUsername("admin");
        account.setPassword("admin");
        account.setStatus(1);
        account.setVerified(1);
        account.setCreatedDate(Instant.now());
        account.setCreatedBy("admin");
        account.setLastModifiedDate(Instant.now());
        account.setLastModifiedBy("admin");

        Users  users = new Users();
        users.setId(1);
        users.setName("Tran Nam Anh");
        users.setBirthday(Instant.now());
        users.setGender(1);
        users.setAddress("Thuong tin - Ha Noi");
        users.setEmail("rubik6958@gmail.com");
        users.setPhone("093938371");
        users.setBalance(1);
        users.setVerified(1);
        users.setStatus(1);
        users.setCreatedDate(Instant.now());
        users.setCreatedBy("admin");
        users.setLastModifiedDate(Instant.now());
        users.setLastModifiedBy("admin");
        userRepository.save(users);
        accountRepository.save(account);

    }
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestApiApplication.class, args);
    }

}
