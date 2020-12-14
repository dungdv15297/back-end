package poly.com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import poly.com.domain.Province;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    @Query(value = "select * from province " +
            "LEFT JOIN ward on province.PROVINCE_ID = ward.PROVINCE_ID " +
            "LEFT JOIN room on room.WARD_ID = ward.ID " +
            "WHERE YEAR(room.CREATED_DATE) = YEAR(NOW()) OR room.CREATED_DATE is null " +
            "GROUP BY province.PROVINCE_ID " +
            "ORDER BY count(room.ROOM_ID) DESC, province.PROVINCE_ID ASC", nativeQuery = true)
    Page<Province> top3Province(PageRequest pageRequest);
}
