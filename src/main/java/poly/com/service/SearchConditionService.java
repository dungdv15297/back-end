package poly.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.com.config.common.exception.ServiceException;
import poly.com.domain.SearchCondition;
import poly.com.repository.SearchConditionRepository;
import poly.com.service.dto.SearchConditionDto;

@Service
@Transactional(rollbackFor = {
        ServiceException.class,
        Exception.class
})
public class SearchConditionService {

    @Autowired
    private SearchConditionRepository searchConditionRepository;

    public SearchConditionDto getTopSearch(String accountId) {
        Integer type = searchConditionRepository.getTopTypeOfRoom(accountId);
        Integer provinceId = searchConditionRepository.getTopProvince(accountId);
        Integer districtId = searchConditionRepository.getTopdistrictId(accountId);
        Integer priceMin = searchConditionRepository.getToppriceMin(accountId);
        Integer priceMax = searchConditionRepository.getToppriceMax(accountId);
        Integer acreageMin = searchConditionRepository.getTopAcreageMin(accountId);
        Integer acreageMax = searchConditionRepository.getTopacreageMax(accountId);
        return SearchConditionDto.builder()
                .accountId(accountId)
                .typeOfRoom(type)
                .districtId(districtId)
                .provinceId(provinceId)
                .acreageMax(acreageMax)
                .acreageMin(acreageMin)
                .priceMax(priceMax)
                .priceMin(priceMin)
                .build();
    }

}
