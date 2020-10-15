package poly.com.repository;

import poly.com.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    @Query(" from Account ac where 1=1 " +
            "and (ac.username =:userName)")
    Account findByUserName(@Param("userName") String userName);

    @Query(" from Account ac where 1=1 " +
            "and (ac.username =:userName)")
    Optional<Account> findUserName(@Param("userName") String userName);

    @Query(" select ac.role from Account ac where 1=1 and ac.id = :id")
    Integer getRoleById(@Param("id") String id);

    @Query(" from Account ac where 1=1 and ac.id = :id")
    Account findById(@Param("id") String id);
}
