package poly.com.client.dto.uploadFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.com.service.dto.PictureDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResponse {
    private PictureDTO picture;
}
