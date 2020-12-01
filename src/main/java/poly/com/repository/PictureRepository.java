package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.com.domain.Picture;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {

    @Query("select p.src from Picture p where p.room.id = :roomId")
    List<String> findSrcByRoomId(@Param("roomId") String roomId);

    @Query("from Picture p where p.room.id = :roomId")
    List<Picture> findByRoomId(@Param("roomId") String roomId);
}
