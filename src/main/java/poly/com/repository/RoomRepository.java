package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.com.domain.Room;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {

    @Query("from  Room r where r.id =:id")
    Optional<Room> findByIdRoom(@Param("id") String id);

    @Modifying
    @Query("delete  from Room r where r.id =:id")
    void deleteByIdRoom(@Param("id")String id);
}
