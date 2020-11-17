package poly.com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.com.domain.Room;

import java.time.Instant;
import java.util.List;
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
            "where 1 " +
            "and (r.ACREAGE_MIN >=:acreageMin or :acreageMin is null) " +
            "and (r.ACREAGE_MAX <=:acreageMax or :acreageMax is null) " +
            "and (r.PRICE_MIN >=:priceMin or :priceMin is null) " +
            "and (r.PRICE_MAX <=:priceMax or :priceMax is null) " +
            "and (dt.DISTRICT_ID = :districtId or :districtId is null) " +
            "and (pr.PROVINCE_ID = :provinceId or :provinceId is null) " +
            "and (r.TYPE_OF = :typeOfRoom or :typeOfRoom is null) " +
            "order by (r.LAST_UP_TOP > :upTopTime) asc, r.UP_TOP_STATUS desc, r.CREATED_DATE desc",
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
}
