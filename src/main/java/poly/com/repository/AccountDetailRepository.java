package poly.com.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import poly.com.domain.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail,Integer> {

    @Query(" FROM AccountDetail a WHERE a.id = :id")
    AccountDetail findById(@Param("id") String id);
}
