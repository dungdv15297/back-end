package poly.com.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poly.com.service.DistrictService;
import poly.com.service.dto.DistrictDto;

import java.util.List;

@RestController
@RequestMapping("/api/district")
@CrossOrigin
public class DistrictResource {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DistrictService districtService;

    @GetMapping("/getAll")
    public List<DistrictDto> getAll() {
        return districtService.getAll();
    }

    @GetMapping("/getByProvinceId")
    public List<DistrictDto> getByProvinceId(@RequestParam("provinceId") Integer provinceId) {
        return districtService.getByProvince(provinceId);
    }
}
