package poly.com.web.rest;

import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import poly.com.client.dto.account.room.*;
import poly.com.client.dto.room.PagingRoomRequest;
import poly.com.client.dto.room.PagingRoomResponse;
import poly.com.client.dto.room.SearchRoomAnyRequest;
import poly.com.config.common.BaseDataRequest;
import poly.com.config.common.BaseDataResponse;
import poly.com.config.common.exception.ServiceException;
import poly.com.config.common.util.ResponseUtil;
import poly.com.domain.Room;
import poly.com.service.RoomService;
import poly.com.service.dto.RoomDTO;
import poly.com.service.dto.Uptop;

import java.util.List;

@RestController
@RequestMapping("/api/room")
@CrossOrigin
public class RoomResource {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RoomService roomService;

    public RoomResource(
            RoomService roomService
    ) {
        this.roomService = roomService;
    }

    @PostMapping("/add-room")
    public ResponseEntity<BaseDataResponse<CreateRoomResponse>> create(
            @RequestBody BaseDataRequest<CreateRoomRequest> request
    ) {
        try {
            CreateRoomResponse response = roomService.createRoom(request.getBody());
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception e) {
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PostMapping("update-room")
    public ResponseEntity<BaseDataResponse<UpdateRoomResponse>> update(
            @RequestBody BaseDataRequest<UpdateRoomRequest> request
    ) {
        try {
            UpdateRoomResponse response = roomService.updateRoom(request.getBody());
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception e) {
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PostMapping("delete-room")
    public ResponseEntity<BaseDataResponse<DeleteRoomResponse>> delete(
            @RequestBody BaseDataRequest<DeleteRoomRequest> request
    ) {
        try {
            DeleteRoomResponse response = roomService.delete(request.getBody());
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            return ResponseUtil.generateErrorResponse(e);

        } catch (Exception e) {
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PostMapping("delete")
    public void deleteRoom(@RequestBody String id) {
        roomService.deleteRoom(id);
    }

    @GetMapping("getPageRoom")
    public Page<Room> getPageRoom(@RequestParam("accountId") String accountId, @RequestParam("page") int page, @RequestParam("size") int size) {
        return roomService.getPageRoom(accountId, page, size);
    }

    @PostMapping("search-room")
    public ResponseEntity<PagingRoomResponse> getRoomByParams(
            @RequestBody PagingRoomRequest request
    ) {
        try {
            PagingRoomResponse response = roomService.getListRoomWithParam(request);
            return ResponseUtil.wrap(response);
        } catch (Exception e) {
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PostMapping("search-room-any")
    public Page<RoomDTO> searchRoomAny(@RequestBody SearchRoomAnyRequest request) {
        return roomService.searchRoomAny(request.getType(),
                request.getProvince(),
                request.getDistrict(),
                request.getAcreage(),
                request.getPrice(),
                request.getPage(),
                request.getSize(),
                request.getAccountId());
    }

    @PostMapping("search-trend-room")
    public Page<RoomDTO> searchTrendRoom(@RequestBody SearchRoomAnyRequest request) {
        return roomService.searchTrendRoom(request.getType(),
                request.getProvince(),
                request.getDistrict(),
                request.getAcreage(),
                request.getPrice(),
                request.getPage(),
                request.getSize());
    }

    @PostMapping("details-room")
    public RoomDTO detailsRoom(@RequestBody String id) {
        return roomService.findDetailRoom(id);
    }

    @PostMapping("uptop")
    public Boolean uptop(@RequestBody RoomDTO roomDto) {
        return roomService.uptop(roomDto);
    }

    @PostMapping("canbeUptop")
    public Uptop canBeUptop(@RequestBody RoomDTO roomDto) {
        return roomService.canBeUptop(roomDto);
    }

    @PostMapping("suggestion")
    public Page<RoomDTO> suggestion(@RequestBody String accountId) {
        return roomService.suggestionsRoom(accountId);
    }

    @PostMapping("sameAs")
    public Page<RoomDTO> sameAs(@RequestBody String roomid) {
        return roomService.sameAs(roomid);
    }
}


