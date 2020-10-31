package poly.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.com.config.common.exception.ServiceException;
import poly.com.domain.Province;
import poly.com.repository.ProvinceRepository;
import poly.com.service.dto.ProvinceDto;

import java.util.List;

@Service
@Transactional(rollbackFor = { ServiceException.class, Exception.class })
public class ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    public List<ProvinceDto> getAll() {
        List<Province> provinces = provinceRepository.findAll(Sort.by("id").ascending());
        return ProvinceDto.builder().build().toDto(provinces);
    }
}
