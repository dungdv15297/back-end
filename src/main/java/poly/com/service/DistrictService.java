package poly.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.com.config.common.exception.ServiceException;
import poly.com.domain.District;
import poly.com.repository.DistrictRepository;
import poly.com.service.dto.DistrictDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = { ServiceException.class, Exception.class })
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    public List<DistrictDto> getAll() {
        return districtRepository.findAll(Sort.by("id").ascending())
                .stream()
                .map(x -> DistrictDto.builder().build().toDto(x))
                .collect(Collectors.toList());
    }

    public List<DistrictDto> getByProvince(Integer provinceId) {
        return districtRepository.findByProvinceId(provinceId)
                .stream()
                .map(x -> DistrictDto.builder().build().toDto(x))
                .collect(Collectors.toList());
    }

    public List<Integer> getData(Integer provinceId) {
        return districtRepository.getData(provinceId);
    }

    public List<Integer> getDataUptop(Integer provinceId, Integer year) {
        return districtRepository.getDataUptop(provinceId, year);
    }

    public List<Integer> getDataUnUptop(Integer provinceId, Integer year) {
        return districtRepository.getDataUnUptop(provinceId, year);
    }
}
