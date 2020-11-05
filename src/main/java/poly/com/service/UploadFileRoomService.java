package poly.com.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import poly.com.Exception.FileStorageException;
import poly.com.Exception.RoomNotFoundException;
import poly.com.client.dto.uploadFile.Base64ConvertToMultipartFile;
import poly.com.client.dto.uploadFile.ImageUploadForm;
import poly.com.client.dto.uploadFile.UploadFileResponse;
import poly.com.client.dto.uploadFile.UploadFilesResponse;
import poly.com.config.Status;
import poly.com.config.common.exception.ServiceException;
import poly.com.config.common.security.SecurityUtils;
import poly.com.domain.Picture;
import poly.com.domain.Room;
import poly.com.repository.PictureRepository;
import poly.com.repository.RoomRepository;
import poly.com.service.dto.PictureDTO;
import poly.com.service.mapper.PictureMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.io.*;

@Service
@Transactional(rollbackFor = {
        ServiceException.class,
        Exception.class
})
public class UploadFileRoomService {

    private PictureRepository pictureRepository;

    private PictureMapper pictureMapper;

    private RoomRepository roomRepository;

    public UploadFileRoomService(
            PictureRepository pictureRepository,
            PictureMapper pictureMapper,
            RoomRepository roomRepository
    ){
        this.pictureRepository= pictureRepository;
        this.pictureMapper = pictureMapper;
        this.roomRepository = roomRepository;
    }

    public UploadFileResponse getFile(Integer id){
        UploadFileResponse response = new UploadFileResponse();
        Picture  picture = pictureRepository.findById(id).orElse(null);
        PictureDTO pictureDTO = pictureMapper.toDto(picture);
        response.setPicture(pictureDTO);
        return response;
    }
    public Stream<Picture> getAllFiles(){
        return pictureRepository.findAll().stream();
    }
    public UploadFilesResponse uploadFiles(MultipartFile[] filesData, String id) throws IOException{
        try {
            if(filesData.length < 0){
                throw new FileStorageException("file not isEmpty");
            }

            Optional<Room> optionalRoom = roomRepository.findByIdRoom(id);
            String uploadRootPath = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "picture-room" + File.separator + "images";
            System.out.println("uploadRootPath:"+uploadRootPath);

            File uploadRootDir =new File(uploadRootPath);
            if(!uploadRootDir.exists()){
                uploadRootDir.mkdirs();
            }
            MultipartFile[] files = filesData;
            List<File> uploadFiles = new ArrayList<>();
            List<PictureDTO> pictureDTOList = new ArrayList<>();
            for(MultipartFile fileData : files){
                String name= fileData.getOriginalFilename();
                String type = fileData.getContentType();

                if(name !=null && name.length() >0){
                    try {
                        File serverFile =new File(uploadRootDir.getAbsolutePath() +File.separator + name);

                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                        stream.write(fileData.getBytes());
                        stream.close();
                        uploadFiles.add(serverFile);
                        System.out.println("write: "+serverFile);

                        Picture picture = new Picture();
                        picture.setRoom(optionalRoom.get());
                        picture.setName(name);
                        picture.setType(type);
                        picture.setSrc(serverFile.getPath());
                        picture.setStatus(Status.Active);
                        picture.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
                        picture.setCreatedDate(Instant.now());
                        picture.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
                        picture.setLastModifiedDate(Instant.now());
                        PictureDTO pictureDTO = pictureMapper.toDto(picture);
                        pictureRepository.save(picture);
                        pictureDTOList.add(pictureDTO);
                    }
                    catch (Exception e){
                        throw e;
                    }
                }
            }

            UploadFilesResponse response = new UploadFilesResponse();
            response.setPictures(pictureDTOList);
            return response;
        }
        catch (IOException ex){
            throw new FileStorageException("upload file not found");
        }
    }
}
