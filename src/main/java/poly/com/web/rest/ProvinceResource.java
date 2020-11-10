package poly.com.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poly.com.service.ProvinceService;
import poly.com.service.dto.ProvinceDto;

import java.util.List;

@RestController
@RequestMapping("/api/province")
@CrossOrigin
public class ProvinceResource {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/getAll")
    public List<ProvinceDto> getAll() {
        return provinceService.getAll();
    }
}
