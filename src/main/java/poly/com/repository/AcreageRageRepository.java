package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.com.domain.AcreageRange;

@Repository
public interface AcreageRageRepository extends JpaRepository<AcreageRange,Integer> {
}
