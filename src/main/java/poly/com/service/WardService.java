package poly.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.com.config.common.exception.ServiceException;
import poly.com.repository.WardRepository;
import poly.com.service.dto.WardDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = { ServiceException.class, Exception.class })
public class WardService {

    @Autowired
    private WardRepository wardRepository;

    public List<WardDto> getByDistrictAndProvince(Integer districtId, Integer pid) {
        return wardRepository.getByDistrictAndProvince(districtId, pid)
                .stream()
                .map(x -> WardDto.builder().build().toDto(x))
                .collect(Collectors.toList());
    }
}
