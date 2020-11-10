package poly.com.client.dto.uploadFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadForm {
    private String fileName;
    private MultipartFile[] files;
    private String imagePath;
    private String roomId;
}
