package poly.com;

import poly.com.config.common.MD5Library;
import poly.com.domain.Account;
import poly.com.domain.AccountDetail;
import poly.com.repository.AccountDetailRepository;
import poly.com.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.UUID;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
