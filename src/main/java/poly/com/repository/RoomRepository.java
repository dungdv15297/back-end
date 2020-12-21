package poly.com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.com.domain.Province;
import poly.com.domain.Room;
import poly.com.service.dto.ProvinceRoomDto;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {

    @Query("from  Room r where r.id =:id")
    Optional<Room> findByIdRoom(@Param("id") String id);

    @Modifying
    @Query("delete  from Room r where r.id =:id")
    Room deleteByIdRoom(@Param("id")String id);

    @Query("from Room r where r.account.id =:id")
    Page<Room> findByAccount_Id(@Param("id") String id, Pageable pageable);

    @Query("select r from Room r inner join Ward w " +
            "on r.ward.id = w.id " +
            "inner join District dt on dt.id = w.district.id " +
            "inner join Province pr on pr.id = w.province.id where 1=1 " +
            "and(r.acreageMin >=:acreageMin or :acreageMin is null) " +
            "and(r.acreageMax <=:acreageMax or :acreageMax is null) " +
            "and(r.priceMin >=:priceMin or :priceMin is null)  " +
            "and(r.priceMax <=:priceMax or :priceMax is null) " +
            "and(lower(dt.name) like concat('%',:districtName,'%') or :districtName is null ) " +
            "group by r.createdDate order by r.createdDate asc ")
    Page<Room> finByRoomWithParam(Pageable pageable,
                                  @Param("acreageMin") Integer acreageMin,
                                  @Param("acreageMax") Integer acreageMax,
                                  @Param("priceMin") Integer priceMin,
                                  @Param("priceMax") Integer priceMax,
                                  @Param("districtName") String districtName
                                  );

    @Query(value = "select * from room r " +
            "left join ward w on r.WARD_ID = w.ID " +
            "left join district dt on dt.DISTRICT_ID = w.DISTRICT_ID " +
            "left join province pr on pr.PROVINCE_ID = w.PROVINCE_ID " +
            "where (r.ACREAGE_MIN <=:acreageMax or :acreageMax is null) " +
            "and (r.ACREAGE_MAX >=:acreageMin or :acreageMin is null) " +
            "and (r.PRICE_MIN <=:priceMax or :priceMax is null) " +
            "and (r.PRICE_MAX >=:priceMin or :priceMin is null) " +
            "and (dt.DISTRICT_ID = :districtId or :districtId is null) " +
            "and (pr.PROVINCE_ID = :provinceId or :provinceId is null) " +
            "and (r.TYPE_OF = :typeOfRoom or :typeOfRoom is null) " +
            "order by " +
            "r.STATUS ASC, " +
            "CASE " +
            "WHEN (r.LAST_UP_TOP > :upTopTime) THEN 1 ELSE 0 " +
            "END desc, " +
            "CASE " +
            "WHEN (r.LAST_UP_TOP > :upTopTime) THEN r.UP_TOP_STATUS ELSE 0 " +
            "END desc, " +
            "CASE WHEN (r.LAST_UP_TOP > DATE_SUB(NOW(), INTERVAL 7 DAY)) THEN r.LAST_UP_TOP END ASC, " +
            "r.CREATED_DATE DESC",
            nativeQuery = true)
    Page<Room> searchRoomAny(Pageable pageable,
                             @Param("acreageMin") Integer acreageMin,
                             @Param("acreageMax") Integer acreageMax,
                             @Param("priceMin") Integer priceMin,
                             @Param("priceMax") Integer priceMax,
                             @Param("districtId") Integer districtId,
                             @Param("provinceId") Integer provinceId,
                             @Param("typeOfRoom") Integer typeOfRoom,
                             @Param("upTopTime") Instant upTopTime);

    @Query("select a.ward.province from Room a group by a.ward.province order by count(a) desc")
    List<Province> top3(PageRequest pageRequest);

    @Query("select a.ward.name, count(a) from Room a where a.ward.province.id = :provinceId group by a.ward")
    LinkedHashMap<String, Integer> getCountRoom(@Param("provinceId") Integer provinceId);

    @Query(value = "SELECT COUNT(*) FROM `room` WHERE LAST_UP_TOP > DATE_SUB(NOW(), INTERVAL 7 DAY) AND YEAR(CREATED_DATE) = YEAR(NOW())", nativeQuery = true)
    Integer countUptop();

    @Query(value = "SELECT COUNT(*) FROM `room` WHERE (LAST_UP_TOP <= DATE_SUB(NOW(), INTERVAL 7 DAY) OR LAST_UP_TOP IS NULL) AND YEAR(CREATED_DATE) = YEAR(NOW())", nativeQuery = true)
    Integer countNotUptop();

    @Query(value = "SELECT COUNT(*) FROM `room` WHERE LAST_UP_TOP > DATE_SUB(NOW(), INTERVAL 7 DAY) AND YEAR(CREATED_DATE) = YEAR(NOW()) AND MONTH(CREATED_DATE) = MONTH(NOW())", nativeQuery = true)
    Integer countMonthUptop();

    @Query(value = "SELECT COUNT(*) FROM `room` WHERE (LAST_UP_TOP <= DATE_SUB(NOW(), INTERVAL 7 DAY) OR LAST_UP_TOP IS NULL) AND YEAR(CREATED_DATE) = YEAR(NOW()) AND MONTH(CREATED_DATE) = MONTH(NOW())", nativeQuery = true)
    Integer countMonthNotUptop();

    @Query(value = "select count(*) from room left join ward on room.WARD_ID = ward.ID " +
            "left join province on ward.PROVINCE_ID = province.PROVINCE_ID " +
            "where province.PROVINCE_ID = :provinceId " +
            "and room.LAST_UP_TOP > DATE_SUB(NOW(), INTERVAL 7 DAY)" +
            "and YEAR(room.CREATED_DATE) = YEAR(NOW())", nativeQuery = true)
    Integer countUptopByProvince(@Param("provinceId") Integer provinceId);

    @Query(value = "select count(*) from room left join ward on room.WARD_ID = ward.ID " +
            "left join province on ward.PROVINCE_ID = province.PROVINCE_ID " +
            "where province.PROVINCE_ID = :provinceId " +
            "and (room.LAST_UP_TOP <= DATE_SUB(NOW(), INTERVAL 7 DAY) OR room.LAST_UP_TOP IS NULL) " +
            "and YEAR(room.CREATED_DATE) = YEAR(NOW())", nativeQuery = true)
    Integer countNotUptopByProvince(@Param("provinceId") Integer provinceId);

//    @Query(value = "select count(*) from room left join ward on room.WARD_ID = ward.ID" +
//            " where ward.PROVINCE_ID = :provinceId" +
//            " AND ward.ID = :wardId" +
//            " AND room.LAST_UP_TOP > DATE_SUB(NOW(), INTERVAL 7 DAY)" +
//            " AND YEAR(room.CREATED_DATE) = YEAR(NOW())", nativeQuery = true)
//    Integer countUptopByWard(@Param("provinceId") Integer provinceId, @Param("wardID") Integer wardId);

//    @Query(value = "select count(*) from room left join ward on room.WARD_ID = ward.ID" +
//            " where ward.PROVINCE_ID = :provinceId" +
//            " AND ward.ID = :wardId" +
//            " AND room.LAST_UP_TOP > DATE_SUB(NOW(), INTERVAL 7 DAY)" +
//            " AND YEAR(room.CREATED_DATE) = YEAR(NOW())", nativeQuery = true)
//    Integer countNotUptopByWard(@Param("provinceId") Integer provinceId, @Param("wardID") Integer wardId);

    @Modifying
    @Query(value = "delete from room where ACCOUNT_ID = :accountId", nativeQuery = true)
    void deleteByAccount(@Param("accountId") String accountId);

}
