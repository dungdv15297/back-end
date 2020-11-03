package poly.com.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poly.com.service.WardService;
import poly.com.service.dto.WardDto;

import java.util.List;

@RestController
@RequestMapping("/api/ward")
@CrossOrigin
public class WardResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WardService wardService;

    @GetMapping("/getByDistrictAndProvince")
    public List<WardDto> getByDistrictId(@RequestParam("districtId") Integer id, @RequestParam("provinceId") Integer pid) {
        return wardService.getByDistrictAndProvince(id, pid);
    }
}
