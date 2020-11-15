package poly.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import poly.com.Exception.RoomNotFoundException;
import poly.com.client.dto.account.room.*;
import poly.com.client.dto.room.FilterRoomRequest;
import poly.com.client.dto.room.PagingRoomRequest;
import poly.com.client.dto.room.PagingRoomResponse;
import poly.com.client.dto.room.GetListRoomRequest;
import poly.com.client.dto.room.GetListRoomResponse;
import poly.com.config.Status;
import poly.com.config.common.ValidationErrorResponse;
import poly.com.config.common.exception.ServiceException;
import poly.com.config.common.security.SecurityUtils;
import poly.com.config.common.util.ServiceExceptionBuilder;
import poly.com.config.common.validationError.ValidationError;
import poly.com.domain.*;
import poly.com.repository.*;
import poly.com.service.dto.*;
import poly.com.service.mapper.AcreageRageMapper;
import poly.com.service.mapper.PriceRageMapper;
import poly.com.service.mapper.RoomMapper;
import poly.com.service.mapper.StreetMapper;

import javax.swing.text.html.Option;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
            return response;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public DeleteRoomResponse deleteRoom(DeleteRoomRequest request) throws ServiceException, Exception {
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
                    searchParams.getStreet().getName() != null ? searchParams.getStreet().getName() : null,
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

    public Page<RoomDTO> searchRoomAny(Integer type, Integer province, Integer district, Integer acreage, Integer price, Integer page, Integer size) {
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
}
