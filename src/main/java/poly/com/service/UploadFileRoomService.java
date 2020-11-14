package poly.com.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.com.Exception.FileStorageException;
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
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    ) {
        this.pictureRepository = pictureRepository;
        this.pictureMapper = pictureMapper;
        this.roomRepository = roomRepository;
    }

    public UploadFileResponse getFile(Integer id) {
        UploadFileResponse response = new UploadFileResponse();
        Picture picture = pictureRepository.findById(id).orElse(null);
        PictureDTO pictureDTO = pictureMapper.toDto(picture);
        response.setPicture(pictureDTO);
        return response;
    }

    public Stream<Picture> getAllFiles() {
        return pictureRepository.findAll().stream();
    }

    public UploadFilesResponse uploadFiles(String[] filesData, String id) throws IOException {
        if (filesData.length < 0) {
            throw new FileStorageException("file not isEmpty");
        }

        Optional<Room> optionalRoom = roomRepository.findByIdRoom(id);
        String uploadRootPath = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "picture-room" + File.separator + "images";
        System.out.println("uploadRootPath:" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        List<File> uploadFiles = new ArrayList<>();
        List<PictureDTO> pictureDTOList = new ArrayList<>();
        for (String fileData : filesData) {
            try {
                String name = UUID.randomUUID().toString() + ".jpeg";
                String pathFile = uploadRootDir.getAbsolutePath() + File.separator + name;

                Picture picture = new Picture();
                picture.setRoom(optionalRoom.get());
                picture.setSrc(pathFile);
                picture.setStatus(Status.Active);
                picture.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
                picture.setCreatedDate(Instant.now());
                picture.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
                picture.setLastModifiedDate(Instant.now());
                PictureDTO pictureDTO = pictureMapper.toDto(picture);
                picture.setName(name);
                pictureRepository.save(picture);
                pictureDTOList.add(pictureDTO);
                ImageBase64.decodeToImage(fileData, pathFile);
            } catch (Exception e) {
                throw e;
            }
        }

        UploadFilesResponse response = new UploadFilesResponse();
        response.setPictures(pictureDTOList);
        return response;
    }

    public List<String> getImages(String roomId) {
        List<String> listSrc = pictureRepository.findSrcByRoomId(roomId);
        if (listSrc.isEmpty()) {
            return new ArrayList<>();
        }
        return listSrc.stream().map(x -> ImageBase64.encoder(x)).collect(Collectors.toList());
    }

}

class ImageBase64 {
    public static BufferedImage decodeToImage(String imageString, String filePath) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            File outputfile = new File(filePath);
            ImageIO.write(image, "png", outputfile);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static String encodeToString(BufferedImage image) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "png", bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = "data:image/png;base64," + encoder.encode(imageBytes);
            imageString.replaceAll("\r\n", "");
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }
}