package poly.com.web.rest;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.com.client.dto.account.MasterTableRequest;
import poly.com.config.common.util.ResponseUtil;
import poly.com.domain.AcreageRange;
import poly.com.domain.PriceRange;
import poly.com.service.MasterTableService;

import java.util.List;

@RestController
@RequestMapping("/api/master")
@CrossOrigin
public class MasterTableResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MasterTableService masterTableService;

    @GetMapping("/price-range/getPage")
    public Page<PriceRange> getAllPricePage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return masterTableService.getAllPrice(page, size);
    }

    @GetMapping("/price-range/getAll")
    public List<PriceRange> getAllPrice() {
        return masterTableService.getAllPrice();
    }

    @PostMapping("/price-range/save")
    public ResponseEntity<Boolean> addPrice(@RequestBody MasterTableRequest params) {
        Boolean result = masterTableService.addPrice(params);
        if (result) {
            return ResponseUtil.wrap(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @PostMapping("/price-range/inactive")
    public ResponseEntity<Boolean> inactivePrice(@RequestBody MasterTableRequest param) {
        Boolean result = masterTableService.inactivePrice(param);
        if (result) {
            return ResponseUtil.wrap(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @PostMapping("/price-range/active")
    public ResponseEntity<Boolean> activePrice(@RequestBody MasterTableRequest param) {
        Boolean result = masterTableService.activePrice(param);
        if (result) {
            return ResponseUtil.wrap(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @PostMapping("/price-range/delete")
    public Boolean deletePrice(@RequestBody Integer id) {
        return masterTableService.deletePriceRange(id);
    }


    @GetMapping("/acreage-range/getPage")
    public Page<AcreageRange> getAllAcreagePage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return masterTableService.getAllAcreage(page, size);
    }

    @GetMapping("/acreage-range/getAll")
    public List<AcreageRange> getAllAcreage() {
        return masterTableService.getAllAcreage();
    }

    @PostMapping("/acreage-range/save")
    public ResponseEntity<Boolean> addAcreage(@RequestBody MasterTableRequest params) {
        Boolean result = masterTableService.addAcreage(params);
        if (result) {
            return  ResponseUtil.wrap(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @PostMapping("/acreage-range/inactive")
    public ResponseEntity<Boolean> inactiveAcreage(@RequestBody MasterTableRequest param) {
        Boolean result = masterTableService.inactiveAcreage(param);
        if (result) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @PostMapping("/acreage-range/delete")
    public Boolean deleteAcreage(@RequestBody Integer id) {
        return masterTableService.deleteAcreaRange(id);
    }

    @PostMapping("/acreage-range/active")
    public ResponseEntity<Boolean> activeAcreage(@RequestBody MasterTableRequest param) {
        Boolean result = masterTableService.activeAcreage(param);
        if (result) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }
}

