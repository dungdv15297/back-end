package poly.com.client.dto.uploadFile;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class Base64ConvertToMultipartFile implements MultipartFile {
    private final byte[] imageContent;
    private final String name;
    private final String fileName;
    private final String contentType;

    public Base64ConvertToMultipartFile(byte[] imageContent,String name, String fileName, String contentType) {
        this.imageContent = imageContent;
        this.name = name;
        this.fileName= fileName;
        this.contentType = contentType;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return imageContent == null || imageContent.length ==0;
    }

    @Override
    public long getSize() {
        return imageContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imageContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imageContent);
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        new FileOutputStream(file).write(imageContent);
    }
}
