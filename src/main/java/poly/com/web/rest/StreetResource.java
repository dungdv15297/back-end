package poly.com.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poly.com.service.StreetService;
import poly.com.service.dto.StreetDTO;

import java.util.List;

@RestController
@RequestMapping("/api/street")
@CrossOrigin
public class StreetResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StreetService streetService;

    @GetMapping("/getByDistrictAndProvince")
    public List<StreetDTO> getByDistrictId(@RequestParam("districtId") Integer id, @RequestParam("provinceId") Integer pid) {
        return streetService.getByDistrictAndProvince(id, pid);
    }
}
