package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import poly.com.domain.Ward;

import java.util.List;

public interface WardRepository extends JpaRepository<Ward, Integer> {

    @Query("from Ward s where s.district.id = :districtId and s.province.id = :pid")
    List<Ward> getByDistrictAndProvince(@Param("districtId") Integer id, @Param("pid") Integer pid);
}
