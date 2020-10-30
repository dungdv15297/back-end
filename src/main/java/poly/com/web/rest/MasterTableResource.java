package poly.com.web.rest;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import poly.com.domain.AcreageRange;
import poly.com.domain.PriceRange;
import poly.com.repository.AcreageRageRepository;
import poly.com.repository.PriceRangeRepository;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/master")
@CrossOrigin
public class MasterTableResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PriceRangeRepository priceRangeRepository;

    @Autowired
    private AcreageRageRepository acreageRageRepository;

    @GetMapping("/price-range/getAll")
    public Page<PriceRange> getAllPrice(@RequestParam("page") int page, @RequestParam("size") int size) {
        Sort sort = Sort.by("createdDate").ascending();
        return priceRangeRepository.findAll(PageRequest.of(page, size, sort));
    }

    @PostMapping("/price-range/save")
    public ResponseEntity<Boolean> addPrice(@RequestBody Params params) {
        if (checkPrice(params)) {
            return ResponseEntity.badRequest().body(false);
        }
        PriceRange pr = new PriceRange();
        if (params.getId() != null) {
            pr.setId(params.getId());
        }
        pr.setMin(params.getMin());
        pr.setMax(params.getMax());
        pr.setDescription(params.getMin() + " - " + params.getMax());
        pr.setStatus(1);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        pr.setCreatedBy(username);
        pr.setCreatedDate(Instant.now());
        pr.setLastModifiedBy(username);
        pr.setLastModifiedDate(Instant.now());
        priceRangeRepository.save(pr);
        return ResponseEntity.ok(true);
    }

    public boolean checkPrice(Params params) {
        Optional<PriceRange> pr = priceRangeRepository.findByMinMax(params.getMin(), params.getMax());
        return pr.isPresent();
    }

    @PostMapping("/price-range/inactive")
    public ResponseEntity<Boolean> inactivePrice(@RequestBody Params param) {
        Optional<PriceRange> opr = priceRangeRepository.findById(param.getId());
        if (opr.isPresent()) {
            PriceRange pr = opr.get();
            pr.setStatus(0);
            priceRangeRepository.save(pr);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @PostMapping("/price-range/active")
    public ResponseEntity<Boolean> activePrice(@RequestBody Params param) {
        Optional<PriceRange> opr = priceRangeRepository.findById(param.getId());
        if (opr.isPresent()) {
            PriceRange pr = opr.get();
            pr.setStatus(1);
            priceRangeRepository.save(pr);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }


    @GetMapping("/acreage-range/getAll")
    public Page<AcreageRange> getAllAcreage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return acreageRageRepository.findAll(PageRequest.of(page, size, Sort.by("createdDate").ascending()));
    }

    public boolean checkAcreage(Params params) {
        Optional<AcreageRange> pr = acreageRageRepository.findByMinMax(params.getMin(), params.getMax());
        return pr.isPresent();
    }

    @PostMapping("/acreage-range/save")
    public ResponseEntity<Boolean> addAcreage(@RequestBody Params params) {
        if (checkAcreage(params)) {
            return ResponseEntity.badRequest().body(false);
        }
        AcreageRange ar = new AcreageRange();
        if (params.getId() != null) {
            ar.setId(params.getId());
        }
        ar.setMin(params.getMin());
        ar.setMax(params.getMax());
        ar.setDescription(params.getMin() + " - " + params.getMax());
        ar.setStatus(1);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ar.setCreatedBy(username);
        ar.setCreatedDate(Instant.now());
        ar.setLastModifiedBy(username);
        ar.setLastModifiedDate(Instant.now());
        acreageRageRepository.save(ar);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/acreage-range/inactive")
    public ResponseEntity<Boolean> inactiveAcreage(@RequestBody Params param) {
        Optional<AcreageRange> oar = acreageRageRepository.findById(param.getId());
        if (oar.isPresent()) {
            AcreageRange ar = oar.get();
            ar.setStatus(0);
            acreageRageRepository.save(ar);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @PostMapping("/acreage-range/active")
    public ResponseEntity<Boolean> activeAcreage(@RequestBody Params param) {
        Optional<AcreageRange> oar = acreageRageRepository.findById(param.getId());
        if (oar.isPresent()) {
            AcreageRange ar = oar.get();
            ar.setStatus(1);
            acreageRageRepository.save(ar);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }
}

@Data
class Params {
    private Integer id;
    private Integer min;
    private Integer max;
}
