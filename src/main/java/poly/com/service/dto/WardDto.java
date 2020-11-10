package poly.com.service.dto;

import lombok.Builder;
import lombok.Data;
import poly.com.domain.District;
import poly.com.domain.Province;
import poly.com.domain.Ward;
import poly.com.service.mapper.EntityMapper;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class WardDto implements EntityMapper<WardDto, Ward>, Serializable {
    private Integer id;
    private Integer districtId;
    private Integer provinceId;
    private String name;
    private Integer status;
    private String prefix;

    @Override
    public Ward toEntity(WardDto dto) {
        Ward d = new Ward();
        d.setId(dto.getId());
        d.setName(dto.getName());
        d.setStatus(dto.getStatus());
        d.setPrefix(dto.getPrefix());
        Province p = new Province();
        p.setId(dto.getProvinceId());
        d.setProvince(p);
        District dt = new District();
        dt.setId(dto.getDistrictId());
        d.setDistrict(dt);
        return d;
    }

    @Override
    public WardDto toDto(Ward entity) {
        return WardDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .prefix(entity.getPrefix())
                .status(entity.getStatus())
                .provinceId(entity.getProvince().getId())
                .districtId(entity.getDistrict().getId())
                .build();
    }

    @Override
    public List<Ward> toEntity(List<WardDto> dtoList) {
        return dtoList.stream().map(x -> toEntity(x)).collect(Collectors.toList());
    }

    @Override
    public List<WardDto> toDto(List<Ward> entityList) {
        return entityList.stream().map(x -> toDto(x)).collect(Collectors.toList());
    }
}
