package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.com.domain.PriceRange;

@Repository
public interface PriceRangeRepository extends JpaRepository<PriceRange,Integer> {
}
