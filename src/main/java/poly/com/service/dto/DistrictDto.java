package poly.com.service.dto;

import lombok.Builder;
import lombok.Data;
import poly.com.domain.District;
import poly.com.domain.Province;
import poly.com.service.mapper.EntityMapper;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DistrictDto implements EntityMapper<DistrictDto, District>, Serializable {

    private Integer id;
    private Integer provinceId;
    private String name;
    private String prefix;
    private Integer status;

    @Override
    public District toEntity(DistrictDto dto) {
        District d = new District();
        d.setId(id);
        d.setName(name);
        d.setStatus(status);
        d.setPrefix(prefix);
        Province p = new Province();
        p.setId(provinceId);
        d.setProvince(p);
        return d;
    }

    @Override
    public DistrictDto toDto(District entity) {
        return DistrictDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .prefix(entity.getPrefix())
                .status(entity.getStatus())
                .provinceId(entity.getProvince().getId())
                .build();
    }

    @Override
    public List<District> toEntity(List<DistrictDto> dtoList) {
        return dtoList.stream().map(x -> toEntity(x)).collect(Collectors.toList());
    }

    @Override
    public List<DistrictDto> toDto(List<District> entityList) {
        return entityList.stream().map(x -> toDto(x)).collect(Collectors.toList());
    }
}
