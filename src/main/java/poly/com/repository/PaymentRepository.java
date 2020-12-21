package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import poly.com.domain.Account;
import poly.com.domain.Payment;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    @Modifying
    @Transactional
    @Query(value = "delete from payment where ACCOUNT_ID = :accountId", nativeQuery = true)
    void deleteByAccount(@Param("accountId") String accountId);
}
