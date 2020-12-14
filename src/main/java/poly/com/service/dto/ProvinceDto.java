package poly.com.service.dto;

import lombok.Builder;
import lombok.Data;
import poly.com.domain.Province;
import poly.com.service.mapper.EntityMapper;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ProvinceDto implements EntityMapper<ProvinceDto, Province>, Serializable {
    private Integer id;
    private String name;
    private Integer status;
    private String code;
    private Integer uptop;
    private Integer unUptop;

    @Override
    public Province toEntity(ProvinceDto dto) {
        Province province = new Province();
        province.setId(id);
        province.setCode(code);
        province.setName(name);
        province.setStatus(status);
        return province;
    }

    @Override
    public ProvinceDto toDto(Province entity) {
        return ProvinceDto.builder().id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public List<Province> toEntity(List<ProvinceDto> dtoList) {
        return dtoList.stream().map(x -> toEntity(x)).collect(Collectors.toList());
    }

    @Override
    public List<ProvinceDto> toDto(List<Province> entityList) {
        return entityList.stream().map(x -> toDto(x)).collect(Collectors.toList());
    }
}
