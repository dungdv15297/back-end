package poly.com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.com.domain.Room;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {

    @Query("from  Room r where r.id =:id")
    Optional<Room> findByIdRoom(@Param("id") String id);

    @Query("delete  from Room r where r.id =:id")
    Room deleteByIdRoom(@Param("id")String id);

    @Query("from Room r where r.account.id =:id")
    Page<Room> findByAccount_Id(@Param("id") String id, Pageable pageable);

    @Query("select r from Room r inner join AcreageRange ar " +
            "on r.acreageRange.id = ar.id " +
            "inner join Street st on " +
            "r.street.id = st.id " +
            "inner join District  dt on dt.id = st.district.id where 1=1 " +
            "and(r.acreageMin >=:acreageMin or :acreageMin is null) " +
            "and(r.acreageMax <=:acreageMax or :acreageMax is null)  " +
            "and(r.priceMin >=:priceMin or :priceMin is null)  " +
            "and(r.priceMax <=:priceMax or :priceMax is null) " +
            "and(lower(st.name ) like concat('%',:name,'%') or :name is null ) " +
            "and(lower(dt.name) like concat('%',:districtName,'%') or :districtName is null ) " +
            "group by r.createdDate order by r.createdDate asc ")
    Page<Room> finByRoomWithParam(Pageable pageable,
                                  @Param("acreageMin") Integer acreageMin,
                                  @Param("acreageMax") Integer acreageMax,
                                  @Param("priceMin") Integer priceMin,
                                  @Param("priceMax") Integer priceMax,
                                  @Param("name") String name,
                                  @Param("districtName") String districtName
                                  );
}
