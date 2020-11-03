package poly.com.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import poly.com.client.dto.account.room.*;
import poly.com.config.common.BaseDataRequest;
import poly.com.config.common.BaseDataResponse;
import poly.com.config.common.exception.ServiceException;
import poly.com.config.common.util.ResponseUtil;
import poly.com.service.RoomService;

import java.util.List;

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
}
