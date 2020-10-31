package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.com.domain.Street;

import java.util.List;

@Repository
public interface StreetRepository extends JpaRepository<Street,Integer> {

    @Query("from Street s where s.district.id = :districtId")
    List<Street> findByDistrictId(@Param("districtId") Integer id);

    @Query("from Street s where s.district.id = :districtId and s.provinceId = :pid")
    List<Street> getByDistrictAndProvince(@Param("districtId") Integer id, @Param("pid") Integer pid);
}
