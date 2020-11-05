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
}
