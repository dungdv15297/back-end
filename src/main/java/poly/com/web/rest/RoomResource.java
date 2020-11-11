package poly.com.web.rest;

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
import poly.com.client.dto.room.GetListRoomRequest;
import poly.com.client.dto.room.GetListRoomResponse;
import poly.com.config.common.BaseDataRequest;
import poly.com.config.common.BaseDataResponse;
import poly.com.config.common.exception.ServiceException;
import poly.com.config.common.util.ResponseUtil;
import poly.com.domain.Room;
import poly.com.service.RoomService;
import poly.com.service.dto.RoomDTO;

import java.util.List;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/api/room")
@CrossOrigin
public class RoomResource {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RoomService roomService;

    public RoomResource(
            RoomService roomService
    ){
        this.roomService = roomService;
    }

    @PostMapping("/add-room")
    public ResponseEntity<BaseDataResponse<CreateRoomResponse>> create(
            @RequestBody BaseDataRequest<CreateRoomRequest> request
    ){
        try {
            CreateRoomResponse response = roomService.createRoom(request.getBody());
            return ResponseUtil.wrap(response);
        }
        catch (ServiceException e){
            return ResponseUtil.generateErrorResponse(e);
        }
        catch (Exception e){
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PostMapping("demo")
    public void uploadFile(@RequestBody MultipartFile files) {
        files.getName();
    }

    @PostMapping("update-room")
    public ResponseEntity<BaseDataResponse<UpdateRoomResponse>> update(
            @RequestBody BaseDataRequest<UpdateRoomRequest> request
    ){
        try {
            UpdateRoomResponse response = roomService.updateRoom(request.getBody());
            return ResponseUtil.wrap(response);
        }catch (ServiceException e){
            return ResponseUtil.generateErrorResponse(e);
        }
        catch (Exception e){
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PostMapping("delete-room")
    public ResponseEntity<BaseDataResponse<DeleteRoomResponse>> delete(
            @RequestBody BaseDataRequest<DeleteRoomRequest> request
    ){
        try {
            DeleteRoomResponse response = roomService.deleteRoom(request.getBody());
            return ResponseUtil.wrap(response);
        }
        catch (ServiceException e){
            return ResponseUtil.generateErrorResponse(e);

        }
        catch (Exception e){
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @GetMapping("getPageRoom")
    public Page<Room> getPageRoom(@RequestParam("accountId") String accountId, @RequestParam("page") int page, @RequestParam("size") int size) {
        return roomService.getPageRoom(accountId, page, size);
    }
    @PostMapping("search-room")
    public ResponseEntity<PagingRoomResponse> getRoomByParams(
            @RequestBody PagingRoomRequest request
            ){
        try {
            PagingRoomResponse response = roomService.getListRoomWithParam(request);
            return ResponseUtil.wrap(response);
        }
        catch (Exception e){
            return ResponseUtil.generateErrorResponse(e);
        }
    }
}

