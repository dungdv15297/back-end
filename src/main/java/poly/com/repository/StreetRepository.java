package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.com.domain.Street;

@Repository
public interface StreetRepository extends JpaRepository<Street,Integer> {
}
