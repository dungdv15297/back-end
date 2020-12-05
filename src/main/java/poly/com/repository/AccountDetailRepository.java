package poly.com.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import poly.com.domain.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail,Integer> {

    @Query(" FROM AccountDetail a WHERE a.id = :id")
    AccountDetail findById(@Param("id") String id);

    @Query(" FROM AccountDetail a WHERE a.id in :listId")
    List<AccountDetail> findByListId(@Param("listId") List<String> listId);

    @Query("select avatar from AccountDetail where id=:id")
    String getAvatar(@Param("id") String id);

}
