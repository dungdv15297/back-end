package poly.com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import poly.com.config.Status;
import poly.com.config.common.ValidationErrorResponse;
import poly.com.config.common.exception.ServiceException;
import poly.com.config.common.util.ServiceExceptionBuilder;
import poly.com.config.common.validationError.ValidationError;
import poly.com.domain.*;
import poly.com.repository.*;
import poly.com.service.dto.RoomDTO;
import poly.com.service.mapper.AcreageRageMapper;
import poly.com.service.mapper.PriceRageMapper;
import poly.com.service.mapper.RoomMapper;
import poly.com.service.mapper.StreetMapper;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(rollbackFor = {
        ServiceException.class,
        Exception.class
})
public class RoomService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    private final PriceRangeRepository priceRangeRepository;

    private final PriceRageMapper priceRageMapper;

    private final AcreageRageRepository acreageRageRepository;

    private final AcreageRageMapper acreageRageMapper;

    private final StreetRepository streetRepository;

    private final StreetMapper streetMapper;

    private  final AccountRepository accountRepository;

    public RoomService(
            RoomRepository roomRepository,
            RoomMapper roomMapper,
            PriceRangeRepository priceRangeRepository,
            PriceRageMapper priceRageMapper,
            AcreageRageRepository acreageRageRepository,
            AcreageRageMapper acreageRageMapper,
            StreetRepository streetRepository,
            StreetMapper streetMapper,
            AccountRepository accountRepository
    ){
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.priceRangeRepository = priceRangeRepository;
        this.priceRageMapper = priceRageMapper;
        this.acreageRageRepository=acreageRageRepository;
        this.acreageRageMapper= acreageRageMapper;
        this.streetRepository = streetRepository;
        this.streetMapper = streetMapper;
        this.accountRepository =  accountRepository;
    }

    public CreateRoomResponse createRoom(CreateRoomRequest request) throws ServiceException,Exception{
        try {
            if(request == null){
                throw new ServiceException("EmptyPayload");
            }
            if(request.getRoom() == null){
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("room", ValidationError.NotNull))
                        .build();
            }
            Optional<Room> optionalRoom = roomRepository.findByIdRoom(request.getRoom().getId());
            if(optionalRoom.isPresent()){
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("roomId",ValidationError.Duplicate))
                        .build();
            }
            Optional<Street> street = streetRepository.findById(request.getRoom().getStreet().getId());
            if(!street.isPresent()){
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("streetId",ValidationError.NotNull))
                        .build();
            }
            Optional<Account> account = accountRepository.findOptById(request.getRoom().getAccount().getId());
            if(!account.isPresent()){
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("accountId",ValidationError.NotNull))
                        .build();
            }
            RoomDTO dto = request.getRoom();
            Room room = roomMapper.toEntity(dto);
            room.setId(UUID.randomUUID().toString());
            room.setStreet(street.get());
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
            room.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            room.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            CreateRoomResponse response = new CreateRoomResponse();
            response.setRoom(roomMapper.toDto(room));

            return response;
        }
        catch (ServiceException e){
            throw e;
        }
        catch (Exception e){
            throw e;
        }
    }
    public UpdateRoomResponse updateRoom(UpdateRoomRequest request) throws ServiceException,Exception{
        try {
            if(request == null){
                throw new ServiceException("EmptyPayload");
            }
            if(request.getRoom() == null){
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("room", ValidationError.NotNull))
                        .build();
            }
            Optional<Room> optionalRoom = roomRepository.findByIdRoom(request.getRoom().getId());
            if(!optionalRoom.isPresent()){
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("roomId",ValidationError.Duplicate))
                        .build();
            }
            Optional<Street> street = streetRepository.findById(request.getRoom().getStreet().getId());
            if(!street.isPresent()){
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("streetId",ValidationError.NotNull))
                        .build();
            }
            Optional<Account> account = accountRepository.findOptById(request.getRoom().getAccount().getId());
            if(!account.isPresent()){
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("accountId",ValidationError.NotNull))
                        .build();
            }
            RoomDTO dto = request.getRoom();
            Room room = roomMapper.toEntity(dto);
            room.setId(request.getRoom().getId());
            room.setStreet(street.get());
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
        }
        catch (ServiceException e){
            throw e;
        }
        catch (Exception e){
            throw e;
        }
    }
    public DeleteRoomResponse deleteRoom(DeleteRoomRequest request) throws ServiceException, Exception{
        try {
            Optional<Room> optionalRoom = roomRepository.findByIdRoom(request.getId());
            if(!optionalRoom.isPresent()){
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("id",ValidationError.NotNull))
                        .build();
            }
            roomRepository.deleteByIdRoom(optionalRoom.get().getId());
            List<Room> list = roomRepository.findAll();
            DeleteRoomResponse response = new DeleteRoomResponse();
            response.setRooms(roomMapper.toDto(list));
            return response;
        } catch (Exception e){
            throw e;
        }
    }

    public Page<Room> getPageRoom(String accountId, int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        return roomRepository.findByAccount_Id(accountId, PageRequest.of(page, size, sort));
    }
    public PagingRoomResponse getListRoomWithParam(PagingRoomRequest request){
        try {
            if(request == null){
                throw new RoomNotFoundException("payloadNotEmpty");
            }
            if(request.getRoom() == null){
                throw  new RoomNotFoundException("room not found");
            }
            if(request.getPageNumber() < 0){
                request.setPageNumber(1);
            }
            if(request.getPageSize() < 0){
                request.setPageSize(1);
            }
            Pageable pageable = PageRequest.of(request.getPageNumber() - 1,
                    request.getPageSize(),
                    Sort.by(Sort.Direction.ASC, StringUtils.isEmpty(request.getOrderBy()) ? "id" : request.getOrderBy()));
            FilterRoomRequest searchParams = request.getRoom();
            Page<RoomDTO> page = roomRepository.finByRoomWithParam(pageable,
                    searchParams.getAcreageMin() !=null ? searchParams.getAcreageMin() : null,
                    searchParams.getAcreageMax() !=null ? searchParams.getAcreageMax() : null,
                    searchParams.getPriceMin() !=null ? searchParams.getPriceMin() : null,
                    searchParams.getPriceMax() !=null ? searchParams.getPriceMax() : null,
                    searchParams.getStreet().getName() !=null ? searchParams.getStreet().getName() : null,
                    searchParams.getDistrict().getName() !=null ? searchParams.getDistrict().getName() : null
                    )
                    .map(roomMapper :: toDto);
            PagingRoomResponse response = new PagingRoomResponse();
            response.setPage(page);
            return response;
        }
        catch (Exception e){
            throw e;
        }
    }
}
