package poly.com.client.dto.uploadFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileRequest {
    private String roomId;
    private MultipartFile[] file;
}
