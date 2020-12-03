package poly.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import poly.com.domain.SearchCondition;

import java.util.List;

@Repository
public interface SearchConditionRepository extends JpaRepository<SearchCondition, Integer> {

    @Query("select s.typeOfRoom from SearchCondition s where s.accountId = :sid group by s.typeOfRoom order by count(s.typeOfRoom) desc")
    List<Integer> getTopTypeOfRoom(@Param("sid") String sid);

    @Query("select s.provinceId from SearchCondition s where s.accountId = :sid group by s.provinceId order by count(s.provinceId) desc")
    List<Integer> getTopProvince(@Param("sid") String sid);

    @Query("select s.districtId from SearchCondition s where s.accountId = :sid group by s.districtId order by count(s.districtId) desc")
    List<Integer> getTopdistrictId(@Param("sid") String sid);

    @Query("select s.priceMax from SearchCondition s where s.accountId = :sid group by s.priceMax order by count(s.priceMax) desc")
    List<Integer> getToppriceMax(@Param("sid") String sid);

    @Query("select s.priceMin from SearchCondition s where s.accountId = :sid group by s.priceMin order by count(s.priceMin) desc")
    List<Integer> getToppriceMin(@Param("sid") String sid);

    @Query("select s.acreageMin from SearchCondition s where s.accountId = :sid group by s.acreageMin order by count(s.acreageMin) desc")
    List<Integer> getTopAcreageMin(@Param("sid") String sid);

    @Query("select s.acreageMax from SearchCondition s where s.accountId = :sid group by s.acreageMax order by count(s.acreageMax) desc")
    List<Integer> getTopacreageMax(@Param("sid") String sid);
}
