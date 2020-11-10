package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.com.domain.AcreageRange;

import java.util.Optional;

@Repository
public interface AcreageRageRepository extends JpaRepository<AcreageRange,Integer> {
    @Query("from AcreageRange r where r.min =:min and r.max =:max")
    Optional<AcreageRange> findByMinMax(@Param("min") Integer min, @Param("max") Integer max);
}
