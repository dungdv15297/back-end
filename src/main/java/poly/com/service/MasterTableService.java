package poly.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.com.client.dto.account.MasterTableRequest;
import poly.com.config.common.exception.ServiceException;
import poly.com.domain.AcreageRange;
import poly.com.domain.PriceRange;
import poly.com.repository.AcreageRageRepository;
import poly.com.repository.PriceRangeRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = { ServiceException.class, Exception.class })
public class MasterTableService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final PriceRangeRepository priceRangeRepository;

    private final AcreageRageRepository acreageRageRepository;

    public MasterTableService(PriceRangeRepository priceRangeRepository, AcreageRageRepository acreageRageRepository) {
        this.priceRangeRepository = priceRangeRepository;
        this.acreageRageRepository = acreageRageRepository;
    }

    public Boolean deleteAcreaRange(Integer id) {
        try {
            Optional<AcreageRange> acre = acreageRageRepository.findById(id);
            if (acre.isPresent()) {
                acreageRageRepository.delete(acre.get());
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean deletePriceRange(Integer id) {
        try {
            Optional<PriceRange> price = priceRangeRepository.findById(id);
            if (price.isPresent()) {
                priceRangeRepository.delete(price.get());
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public Page<PriceRange> getAllPrice(int page, int size) {
        Sort sort = Sort.by("createdDate").ascending();
        return priceRangeRepository.findAll(PageRequest.of(page, size, sort));
    }

    public List<PriceRange> getAllPrice() {
        Sort sort = Sort.by("min").ascending().and(Sort.by("max").ascending());
        return priceRangeRepository.findAll(sort);
    }

    public Boolean addPrice(MasterTableRequest params) {
        if (checkPrice(params)) {
            return false;
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
        return true;
    }

    public Boolean inactivePrice(MasterTableRequest param) {
        Optional<PriceRange> opr = priceRangeRepository.findById(param.getId());
        if (opr.isPresent()) {
            PriceRange pr = opr.get();
            pr.setStatus(0);
            priceRangeRepository.save(pr);
            return true;
        }
        return false;
    }

    public Boolean activePrice(MasterTableRequest param) {
        Optional<PriceRange> opr = priceRangeRepository.findById(param.getId());
        if (opr.isPresent()) {
            PriceRange pr = opr.get();
            pr.setStatus(1);
            priceRangeRepository.save(pr);
            return true;
        }
        return false;
    }

    public Page<AcreageRange> getAllAcreage(int page, int size) {
        return acreageRageRepository.findAll(PageRequest.of(page, size, Sort.by("createdDate").ascending()));
    }

    public List<AcreageRange> getAllAcreage() {
        return acreageRageRepository.findAll(Sort.by("min").ascending().and(Sort.by("max").ascending()));
    }

    public Boolean addAcreage(MasterTableRequest params) {
        if (checkAcreage(params)) {
            return false;
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
        return true;
    }

    public Boolean inactiveAcreage(MasterTableRequest param) {
        Optional<AcreageRange> oar = acreageRageRepository.findById(param.getId());
        if (oar.isPresent()) {
            AcreageRange ar = oar.get();
            ar.setStatus(0);
            acreageRageRepository.save(ar);
            return true;
        }
        return false;
    }

    public Boolean activeAcreage(MasterTableRequest param) {
        Optional<AcreageRange> oar = acreageRageRepository.findById(param.getId());
        if (oar.isPresent()) {
            AcreageRange ar = oar.get();
            ar.setStatus(1);
            acreageRageRepository.save(ar);
            return true;
        }
        return false;
    }

    public boolean checkPrice(MasterTableRequest params) {
        Optional<PriceRange> pr = priceRangeRepository.findByMinMax(params.getMin(), params.getMax());
        return pr.isPresent();
    }

    public boolean checkAcreage(MasterTableRequest params) {
        Optional<AcreageRange> pr = acreageRageRepository.findByMinMax(params.getMin(), params.getMax());
        return pr.isPresent();
    }

}

