package poly.com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.com.domain.PriceRange;

import java.util.Optional;

@Repository
public interface PriceRangeRepository extends JpaRepository<PriceRange,Integer> {

    @Query("from PriceRange r where r.min =:min and r.max =:max")
    Optional<PriceRange> findByMinMax(@Param("min") Integer min, @Param("max") Integer max);
}
