package poly.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.util.Streamable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import poly.com.Exception.RoomNotFoundException;
import poly.com.client.dto.account.room.*;
import poly.com.client.dto.room.FilterRoomRequest;
import poly.com.client.dto.room.PagingRoomRequest;
import poly.com.client.dto.room.PagingRoomResponse;
import poly.com.config.Status;
import poly.com.config.common.ValidationErrorResponse;
import poly.com.config.common.exception.ServiceException;
import poly.com.config.common.security.SecurityUtils;
import poly.com.config.common.util.ServiceExceptionBuilder;
import poly.com.config.common.validationError.ValidationError;
import poly.com.domain.*;
import poly.com.repository.*;
import poly.com.service.dto.*;
import poly.com.service.mapper.RoomMapper;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {
        ServiceException.class,
        Exception.class
})
public class RoomService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoomRepository roomRepository;

    private RoomMapper roomMapper;

    @Autowired
    private WardRepository wardRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PriceRangeRepository priceRangeRepository;

    @Autowired
    private AcreageRageRepository acreageRageRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private UploadFileRoomService uploadFileRoomService;

    @Autowired
    private SearchConditionRepository searchConditionRepository;

    @Autowired
    private SearchConditionService searchConditionService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private DistrictService districtService;

    public RoomService(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    public CreateRoomResponse createRoom(CreateRoomRequest request) throws ServiceException, Exception {
        try {
            if (request == null) {
                throw new ServiceException("EmptyPayload");
            }
            if (request.getRoom() == null) {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("room", ValidationError.NotNull))
                        .build();
            }
            Optional<Room> optionalRoom = roomRepository.findByIdRoom(request.getRoom().getId());
            if (optionalRoom.isPresent()) {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("roomId", ValidationError.Duplicate))
                        .build();
            }
            Optional<Ward> ward = wardRepository.findById(request.getRoom().getWardId());
            if (!ward.isPresent()) {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("wardId", ValidationError.NotNull))
                        .build();
            }
            Optional<Account> account = accountRepository.findOptById(request.getRoom().getAccount().getId());

            if (!account.isPresent()) {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("accountId", ValidationError.NotNull))
                        .build();
            }
            RoomDTO dto = request.getRoom();
            Room room = roomMapper.toEntity(dto);
            room.setId(UUID.randomUUID().toString());
            room.setWard(ward.get());
            room.setAccount(account.get());
            room.setAddress(request.getRoom().getAddress());
            room.setDescription(request.getRoom().getDescription());
            room.setPriceMin(request.getRoom().getPriceMin());
            room.setPriceMax(request.getRoom().getPriceMax());
            room.setAcreageMin(request.getRoom().getAcreageMin());
            room.setAcreageMax(request.getRoom().getAcreageMax());
            room.setLongtitude(request.getRoom().getLongtitude());
            room.setLatitude(request.getRoom().getLatitude());
            room.setCreatedDate(Instant.now());
            room.setTypeOfRoom(request.getRoom().getTypeOfRoom());
            room.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
            room.setLastModifiedDate(Instant.now());
            room.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
            room.setStatus(Status.Active);
            room.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            room.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

            if (dto.getUpTopStatus() == 1) {
                room.setUpTopStatus(1);
                room.setLastUpTop(Instant.now());
                AccountDetail accountDetail = accountDetailRepository.findById(account.get().getId());
                Integer balance = accountDetail.getBalance();
                balance -= 10000;
                accountDetail.setBalance(balance);
                accountDetailRepository.save(accountDetail);
            }

            Room domain = roomRepository.save(room);
            RoomDTO roomDto = new RoomDTO();
            roomDto.setId(domain.getId());
            return new CreateRoomResponse(roomDto);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public UpdateRoomResponse updateRoom(UpdateRoomRequest request) throws ServiceException, Exception {
        try {
            if (request == null) {
                throw new ServiceException("EmptyPayload");
            }
            if (request.getRoom() == null) {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("room", ValidationError.NotNull))
                        .build();
            }
            Optional<Room> optionalRoom = roomRepository.findByIdRoom(request.getRoom().getId());
            if (!optionalRoom.isPresent()) {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("roomId", ValidationError.Duplicate))
                        .build();
            }
            Optional<Ward> ward = wardRepository.findById(request.getRoom().getWardId());
            if (!ward.isPresent()) {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("streetId", ValidationError.NotNull))
                        .build();
            }

            Optional<Account> account = accountRepository.findOptById(request.getRoom().getAccount().getId());

            if (!account.isPresent()) {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("accountId", ValidationError.NotNull))
                        .build();
            }
            RoomDTO dto = request.getRoom();
            Room room = roomMapper.toEntity(dto);
            room.setLastUpTop(optionalRoom.get().getLastUpTop());
            room.setUpTopStatus(optionalRoom.get().getUpTopStatus());
            room.setId(request.getRoom().getId());
            room.setWard(ward.get());
            room.setAccount(account.get());
            room.setAddress(request.getRoom().getAddress());
            room.setDescription(request.getRoom().getDescription());
            room.setPriceMin(request.getRoom().getPriceMin());
            room.setPriceMax(request.getRoom().getPriceMax());
            room.setAcreageMin(request.getRoom().getAcreageMin());
            room.setAcreageMax(request.getRoom().getAcreageMax());
            room.setLongtitude(request.getRoom().getLongtitude());
            room.setLatitude(request.getRoom().getLatitude());
            room.setStatus(Status.Active);
            room.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            room.setLastModifiedDate(Instant.now());

            UpdateRoomResponse response = new UpdateRoomResponse();
            response.setRoom(roomMapper.toDto(room));
            roomRepository.save(room);
            uploadFileRoomService.removeImage(room.getId());
            return response;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public DeleteRoomResponse delete(DeleteRoomRequest request) throws ServiceException, Exception {
        try {
            Optional<Room> optionalRoom = roomRepository.findByIdRoom(request.getId());
            if (!optionalRoom.isPresent()) {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("id", ValidationError.NotNull))
                        .build();
            }
            roomRepository.deleteByIdRoom(optionalRoom.get().getId());
            List<Room> list = roomRepository.findAll();
            DeleteRoomResponse response = new DeleteRoomResponse();
            response.setRooms(roomMapper.toDto(list));
            return response;
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteRoom(String id) {
        Optional<Room> roomOpt = roomRepository.findByIdRoom(id);
        if (!roomOpt.isPresent()) {
            return;
        }
        roomRepository.delete(roomOpt.get());
    }

    public Page<Room> getPageRoom(String accountId, int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        return roomRepository.findByAccount_Id(accountId, PageRequest.of(page, size, sort));
    }

    public PagingRoomResponse getListRoomWithParam(PagingRoomRequest request) {
        try {
            if (request == null) {
                throw new RoomNotFoundException("payloadNotEmpty");
            }
            if (request.getRoom() == null) {
                throw new RoomNotFoundException("room not found");
            }
            if (request.getPageNumber() < 0) {
                request.setPageNumber(1);
            }
            if (request.getPageSize() < 0) {
                request.setPageSize(1);
            }
            Pageable pageable = PageRequest.of(request.getPageNumber() - 1,
                    request.getPageSize(),
                    Sort.by(Sort.Direction.ASC, StringUtils.isEmpty(request.getOrderBy()) ? "id" : request.getOrderBy()));
            FilterRoomRequest searchParams = request.getRoom();
            Page<RoomDTO> page = roomRepository.finByRoomWithParam(pageable,
                    searchParams.getAcreageMin() != null ? searchParams.getAcreageMin() : null,
                    searchParams.getAcreageMax() != null ? searchParams.getAcreageMax() : null,
                    searchParams.getPriceMin() != null ? searchParams.getPriceMin() : null,
                    searchParams.getPriceMax() != null ? searchParams.getPriceMax() : null,
                    searchParams.getDistrict().getName() != null ? searchParams.getDistrict().getName() : null
            )
                    .map(roomMapper::toDto);
            PagingRoomResponse response = new PagingRoomResponse();
            response.setPage(page);
            return response;
        } catch (Exception e) {
            throw e;
        }
    }

    public Page<RoomDTO> searchRoomAny(Integer type, Integer province, Integer district, Integer acreage, Integer price, Integer page, Integer size, String accountId) {
        Instant today = Instant.now();
        Instant seventDayBefore = today.minus(Duration.ofDays(7));
        Optional<AcreageRange> acreageRange = acreage == null ? Optional.empty() : acreageRageRepository.findById(acreage);
        Optional<PriceRange> priceRange = price == null ? Optional.empty() : priceRangeRepository.findById(price);
        Integer acreageMin = acreageRange.map(AcreageRange::getMin).orElse(null);
        Integer acreageMax = acreageRange.map(AcreageRange::getMax).orElse(null);
        Integer priceMin = priceRange.map(PriceRange::getMin).orElse(null);
        Integer priceMax = priceRange.map(PriceRange::getMax).orElse(null);
        if (accountId != null && !accountId.isEmpty()) {
            SearchCondition searchCondition = SearchCondition.builder()
                    .typeOfRoom(type)
                    .provinceId(province)
                    .districtId(district)
                    .priceMin(priceMin)
                    .priceMax(priceMax)
                    .acreageMin(acreageMin)
                    .acreageMax(acreageMax)
                    .accountId(accountId)
                    .build();
            searchConditionRepository.save(searchCondition);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<RoomDTO> pageRoom = roomRepository.searchRoomAny(pageable, acreageMin, acreageMax, priceMin, priceMax, district, province, type, seventDayBefore)
                .map(roomMapper::toDto);
        pageRoom.forEach(x -> {
            x.setIsUptop(x.getLastUpTop() != null && x.getLastUpTop().isAfter(seventDayBefore));
            List<String> listSrc = pictureRepository.findSrcByRoomId(x.getId());
            if (!listSrc.isEmpty()) {
                x.setImage(ImageBase64.encoder(listSrc.get(0)));
            }
        });
        return pageRoom;
    }

    public Page<RoomDTO> searchTrendRoom(Integer type, Integer province, Integer district, Integer acreage, Integer price, Integer page, Integer size) {
        Instant today = Instant.now();
        Instant seventDayBefore = today.minus(Duration.ofDays(7));
        Optional<AcreageRange> acreageRange = acreage == null ? Optional.empty() : acreageRageRepository.findById(acreage);
        Optional<PriceRange> priceRange = price == null ? Optional.empty() : priceRangeRepository.findById(price);
        Integer acreageMin = acreageRange.map(AcreageRange::getMin).orElse(null);
        Integer acreageMax = acreageRange.map(AcreageRange::getMax).orElse(null);
        Integer priceMin = priceRange.map(PriceRange::getMin).orElse(null);
        Integer priceMax = priceRange.map(PriceRange::getMax).orElse(null);

        Pageable pageable = PageRequest.of(page, size);
        Page<RoomDTO> pageRoom = roomRepository.searchRoomAny(pageable, acreageMin, acreageMax, priceMin, priceMax, district, province, type, seventDayBefore)
                .map(roomMapper::toDto);
        pageRoom.forEach(x -> {
            x.setIsUptop(x.getLastUpTop() != null && x.getLastUpTop().isAfter(seventDayBefore));
            List<String> listSrc = pictureRepository.findSrcByRoomId(x.getId());
            if (!listSrc.isEmpty()) {
                x.setImage(ImageBase64.encoder(listSrc.get(0)));
            }
        });
        return pageRoom;
    }

    public RoomDTO findDetailRoom(String id) {
        Optional<Room> room = roomRepository.findByIdRoom(id);
        if (!room.isPresent()) {
            return null;
        }
        Room domain = room.get();
        RoomDTO dto = roomMapper.toDto(domain);
        Account account = domain.getAccount();
        AccountDetail accountDetail = accountDetailRepository.findById(account.getId());
        if (accountDetail == null) {
            return null;
        }
        dto.setPhone(accountDetail.getPhone());
        List<String> listSrc = pictureRepository.findSrcByRoomId(domain.getId());
        if (!listSrc.isEmpty()) {
            List<String> images = listSrc.stream().map(x -> ImageBase64.encoder(x)).collect(Collectors.toList());
            dto.setPictures(images);
        }
        Ward ward = domain.getWard();
        Province province = ward.getProvince();
        District district = ward.getDistrict();
        WardDto wardDto = WardDto.builder().build();
        ProvinceDto provinceDto = ProvinceDto.builder().build();
        DistrictDto districtDto = DistrictDto.builder().build();
        dto.setWard(wardDto.toDto(ward));
        dto.setDistrict(districtDto.toDto(district));
        dto.setProvince(provinceDto.toDto(province));
        dto.setCreatedBy(accountDetail.getName() + " - " + account.getUsername());
        dto.setCreatedDate(domain.getCreatedDate().toString());
        dto.setUpdatedDate(domain.getLastModifiedDate().toString());
        return dto;
    }

    public Boolean uptop(RoomDTO dto) {
        Optional<Room> room = roomRepository.findByIdRoom(dto.getId());
        if (!room.isPresent()) {
            return false;
        }

        AccountDetail accountDetail = accountDetailRepository.findById(room.get().getAccount().getId());
        Integer balance = accountDetail.getBalance();
        balance -= 10000;
        accountDetail.setBalance(balance);
        accountDetailRepository.save(accountDetail);

        Room domain = room.get();
        Integer uptopNo = domain.getUpTopStatus();
        domain.setUpTopStatus(uptopNo++);
        domain.setLastUpTop(Instant.now());
        roomRepository.save(domain);
        return true;
    }

    public Uptop canBeUptop(RoomDTO dto) {
        Optional<Room> room = roomRepository.findByIdRoom(dto.getId());
        if (!room.isPresent()) {
            return Uptop.builder().accept(false).build();
        }

        if (room.get().getStatus() == 2) {
            return Uptop.builder()
                .statusReject(true)
                .build();
        }

        Instant lastUptop = room.get().getLastUpTop();
        if (lastUptop == null) {
            return Uptop.builder().accept(true).build();
        }
        Instant time = lastUptop.plus(Duration.ofHours(4));
        return Uptop.builder()
                .accept(time.isBefore(Instant.now()))
                .time(time.toString())
                .build();
    }

    public Page<RoomDTO> suggestionsRoom(String accountId) {
        SearchConditionDto searchConditionDto = searchConditionService.getTopSearch(accountId);
        Integer acreageMin = searchConditionDto.getAcreageMin();
        Integer acreageMax = searchConditionDto.getAcreageMax();
        Integer priceMin = searchConditionDto.getPriceMin();
        Integer priceMax = searchConditionDto.getPriceMax();
        Integer district = searchConditionDto.getDistrictId();
        Integer province = searchConditionDto.getProvinceId();
        Integer type = searchConditionDto.getTypeOfRoom();
        Instant today = Instant.now();
        Instant seventDayBefore = today.minus(Duration.ofDays(7));
        Pageable pageable = PageRequest.of(0, 6);
        Page<RoomDTO> pageRoom = roomRepository.searchRoomAny(pageable, acreageMin, acreageMax, priceMin, priceMax, district, province, type, seventDayBefore)
                .map(roomMapper::toDto);
        if (pageRoom.isEmpty()) {
            pageRoom = roomRepository.searchRoomAny(pageable, null, null, null, null, district, province, null, seventDayBefore)
                .map(roomMapper::toDto);
        }

        if (pageRoom.isEmpty()) {
            pageRoom = roomRepository.searchRoomAny(pageable, null, null, null, null, null, province, null, seventDayBefore)
                    .map(roomMapper::toDto);
        }
        pageRoom.forEach(x -> {
            x.setIsUptop(x.getLastUpTop() != null && x.getLastUpTop().isAfter(seventDayBefore));
            List<String> listSrc = pictureRepository.findSrcByRoomId(x.getId());
            if (!listSrc.isEmpty()) {
                x.setImage(ImageBase64.encoder(listSrc.get(0)));
            }
        });
        return pageRoom;
    }

    public Page<RoomDTO> sameAs(String roomid) {
        Optional<Room> roomOpt = roomRepository.findByIdRoom(roomid);
        if (!roomOpt.isPresent()) {
            return null;
        }
        Instant today = Instant.now();
        Instant seventDayBefore = today.minus(Duration.ofDays(7));
        Room room = roomOpt.get();
        Integer priceMin = room.getPriceMin();
        Integer priceMax = room.getPriceMax();
        Integer acreageMin = room.getAcreageMin();
        Integer acreageMax = room.getAcreageMax();
        Ward ward = room.getWard();
        Integer district = ward.getDistrict().getId();
        Integer province = ward.getProvince().getId();
        Integer type = room.getTypeOfRoom();
        Pageable pageable = PageRequest.of(0, 10);
        Page<RoomDTO> pageRoom = roomRepository.searchRoomAny(pageable, acreageMin, acreageMax, priceMin, priceMax, district, province, type, seventDayBefore)
                .map(roomMapper::toDto);
        if (pageRoom.isEmpty()) {
            pageRoom = roomRepository.searchRoomAny(pageable, null, null, null, null, district, province, null, seventDayBefore)
                    .map(roomMapper::toDto);
        }
        pageRoom.forEach(x -> {
            x.setIsUptop(x.getLastUpTop() != null && x.getLastUpTop().isAfter(seventDayBefore));
            List<String> listSrc = pictureRepository.findSrcByRoomId(x.getId());
            if (!listSrc.isEmpty()) {
                x.setImage(ImageBase64.encoder(listSrc.get(0)));
            }
        });
        List<RoomDTO> result = pageRoom.stream().filter(x -> !x.getId().equalsIgnoreCase(roomid)).collect(Collectors.toList());
        return new PageImpl<>(result);
    }

    public List<String> top3() {
        List<String> listId = roomRepository.top3(PageRequest.of(0, 3)).stream().map(x -> x.getId().toString()).collect(Collectors.toList());
        return listId;
    }

    public DashboardInfor getDashboardInfor() {
        Integer uptop = roomRepository.countUptop();
        Integer notUptop = roomRepository.countNotUptop();
        Integer monthUptop = roomRepository.countMonthUptop();
        Integer monthNotUptop = roomRepository.countMonthNotUptop();
        return DashboardInfor.builder()
                .uptop(uptop)
                .notUptop(notUptop)
                .monthNotUptop(monthNotUptop)
                .monthUptop(monthUptop)
                .month(Calendar.getInstance().get(Calendar.MONTH))
                .year(Calendar.getInstance().get(Calendar.YEAR))
                .build();
    }

    public Page<ProvinceDto> getProvinceStastical(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Province> provinces = provinceService.getAll(pageRequest);
        Page<ProvinceDto> provinceDtos = provinces.map(x -> {
            Integer id = x.getId();
            Integer uptop = roomRepository.countUptopByProvince(id);
            Integer unUptop = roomRepository.countNotUptopByProvince(id);
            return ProvinceDto.builder().id(x.getId())
                    .code(x.getCode())
                    .name(x.getName())
                    .uptop(uptop)
                    .unUptop(unUptop)
                    .build();
        });
        return provinceDtos;
    }

    public List<Top3Province> top3Province() {
        List<ProvinceDto> provinces = provinceService.top3Province();
        List<Top3Province> top3Provinces = new ArrayList<>();
        provinces.forEach(province -> {
            List<DistrictDto> districts = districtService.getByProvince(province.getId());
            List<String> labels = districts.stream().map(x -> x.getName()).collect(Collectors.toList());
            List<Integer> data = districtService.getData(province.getId());
            top3Provinces.add(Top3Province.builder().provinceId(province.getId()).provinceName(province.getName()).labels(labels).data(data).build());
        });
        return top3Provinces;
    }

    public ProvinceDetail provinceDetail(Integer provinceId, Integer year) {
        List<DistrictDto> districtDtos = districtService.getByProvince(provinceId);
        List<String> labels = districtDtos.stream().map(x -> x.getName()).collect(Collectors.toList());
        List<Integer> uptop = districtService.getDataUptop(provinceId, year);
        List<Integer> unuptop = districtService.getDataUnUptop(provinceId, year);
        return ProvinceDetail.builder()
                .labels(labels)
                .uptop(uptop)
                .unuptop(unuptop)
                .build();
    }

    public Boolean updateStatus(String roomId, Integer status) {
        try {
            Optional<Room> room = roomRepository.findByIdRoom(roomId);
            if (!room.isPresent()) {
                return false;
            }
            Room domain = room.get();
            domain.setStatus(status);
            domain.setLastModifiedDate(Instant.now());
            if (status == 0) {
                domain.setLastUpTop(null);
                domain.setUpTopStatus(0);
            }
            roomRepository.save(domain);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public void deleteByAccount(String accountId) {
        roomRepository.deleteByAccount(accountId);
    }
}
