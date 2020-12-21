package poly.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.com.config.common.exception.ServiceException;
import poly.com.domain.SearchCondition;
import poly.com.repository.SearchConditionRepository;
import poly.com.service.dto.SearchConditionDto;

import java.util.List;

@Service
@Transactional(rollbackFor = {
        ServiceException.class,
        Exception.class
})
public class SearchConditionService {

    @Autowired
    private SearchConditionRepository searchConditionRepository;

    public SearchConditionDto getTopSearch(String accountId) {
        List<Integer> type = searchConditionRepository.getTopTypeOfRoom(accountId);
        List<Integer> provinceId = searchConditionRepository.getTopProvince(accountId);
        List<Integer> districtId = searchConditionRepository.getTopdistrictId(accountId);
        List<Integer> priceMin = searchConditionRepository.getToppriceMin(accountId);
        List<Integer> priceMax = searchConditionRepository.getToppriceMax(accountId);
        List<Integer> acreageMin = searchConditionRepository.getTopAcreageMin(accountId);
        List<Integer> acreageMax = searchConditionRepository.getTopacreageMax(accountId);
        return SearchConditionDto.builder()
                .accountId(accountId)
                .typeOfRoom(type.isEmpty() ? null : type.get(0))
                .districtId(districtId.isEmpty() ? null : districtId.get(0))
                .provinceId(provinceId.isEmpty() ? null : provinceId.get(0))
                .acreageMax(acreageMax.isEmpty() ? null : acreageMax.get(0))
                .acreageMin(acreageMin.isEmpty() ? null : acreageMin.get(0))
                .priceMax(priceMax.isEmpty() ? null : priceMax.get(0))
                .priceMin(priceMin.isEmpty() ? null : priceMin.get(0))
                .build();
    }

    public void deleteByAccount(String accountId) {
        searchConditionRepository.deleteByAccount(accountId);
    }

}
