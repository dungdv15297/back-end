package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import poly.com.domain.District;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {

    @Query("from District r where r.province.id =:pid order by r.id asc")
    List<District> findByProvinceId(@Param("pid") Integer pid);

    @Query(value = "SELECT COUNT(room.ROOM_ID) from district LEFT JOIN ward on district.DISTRICT_ID = ward.DISTRICT_ID " +
            "LEFT JOIN room on room.WARD_ID = ward.ID " +
            "where district.PROVINCE_ID = :provinceId AND (YEAR(room.CREATED_DATE) = YEAR(NOW()) OR room.CREATED_DATE IS NULL) " +
            "GROUP BY district.DISTRICT_ID " +
            "ORDER BY district.DISTRICT_ID ASC", nativeQuery = true)
    List<Integer> getData(@Param("provinceId") Integer provinceId);

    @Query(value = "SELECT COUNT(room.ROOM_ID) from district LEFT JOIN ward on district.DISTRICT_ID = ward.DISTRICT_ID " +
            "LEFT JOIN room on room.WARD_ID = ward.ID " +
            "where district.PROVINCE_ID = :provinceId AND (YEAR(room.CREATED_DATE) = :year OR room.CREATED_DATE IS NULL) " +
            "and (room.LAST_UP_TOP > DATE_SUB(NOW(), INTERVAL 7 DAY) OR room.CREATED_DATE IS NULL)" +
            "GROUP BY district.DISTRICT_ID " +
            "ORDER BY district.DISTRICT_ID ASC", nativeQuery = true)
    List<Integer> getDataUptop(@Param("provinceId") Integer provinceId, @Param("year") Integer year);

    @Query(value = "SELECT COUNT(room.ROOM_ID) from district LEFT JOIN ward on district.DISTRICT_ID = ward.DISTRICT_ID " +
            "LEFT JOIN room on room.WARD_ID = ward.ID " +
            "where district.PROVINCE_ID = :provinceId AND (YEAR(room.CREATED_DATE) = :year OR room.CREATED_DATE IS NULL) " +
            "and (room.LAST_UP_TOP <= DATE_SUB(NOW(), INTERVAL 7 DAY) OR room.LAST_UP_TOP IS NULL)" +
            "GROUP BY district.DISTRICT_ID " +
            "ORDER BY district.DISTRICT_ID ASC", nativeQuery = true)
    List<Integer> getDataUnUptop(@Param("provinceId") Integer provinceId, @Param("year") Integer year);
}
