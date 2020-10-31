package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import poly.com.domain.District;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {

    @Query("from District r where r.province.id =:pid")
    List<District> findByProvinceId(@Param("pid") Integer pid);
}
