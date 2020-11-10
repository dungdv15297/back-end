package poly.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.com.config.common.exception.ServiceException;
import poly.com.domain.Street;
import poly.com.repository.StreetRepository;
import poly.com.service.dto.StreetDTO;
import poly.com.service.mapper.StreetMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = { ServiceException.class, Exception.class })
public class StreetService {

    @Autowired
    private StreetRepository streetRepository;

    public List<StreetDTO> getAll() {
        return streetRepository.findAll()
                .stream()
                .map(x -> toDto(x))
                .collect(Collectors.toList());
    }

    public List<StreetDTO> getByDistrictAndProvince(Integer districtId, Integer pid) {
        return streetRepository.getByDistrictAndProvince(districtId, pid)
                .stream()
                .map(x -> toDto(x))
                .collect(Collectors.toList());
    }

    public StreetDTO toDto(Street entity) {
        return StreetDTO.builder()
                .id(entity.getId())
                .districtId(entity.getDistrict().getId())
                .name(entity.getName())
                .prefix(entity.getPrefix())
                .provinceId(entity.getProvinceId())
                .build();
    }
}
