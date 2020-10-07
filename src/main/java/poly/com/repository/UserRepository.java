package poly.com.repository;

import poly.com.domain.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AccountDetail,Integer> {
}
