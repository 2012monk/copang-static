package com.example.copangstatic.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
public class Uploader {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private boolean uploadPublic;

    private final AmazonS3Client s3Client;

    public String upload(MultipartFile multipartFile, String... dirName) throws IOException {
        File file = convert(multipartFile).orElseThrow(
            () -> new IllegalArgumentException("File convert 실패")
        );

        String path = String.join("/",dirName);
        return upload(multipartFile, path);
    }

    public String upload(MultipartFile multipartFile, String path) throws IOException {
        File file = convert(multipartFile).orElseThrow(
            () -> new IllegalArgumentException("File convert 실패")
        );
        return upload(file, path);
    }

    private String upload(File uploadFile, String path) {
        String uploadUrl = putS3(uploadFile, path);
        removeFile(uploadFile);
        return uploadUrl;
    }



//    private String upload(File uploadFile, String dirName)  {
//        String fileName = dirName + "/" + uploadFile.getName();
//        String uploadUrl = putS3(uploadFile, fileName);
//        removeFile(uploadFile);
//        return uploadUrl;
//    }

    private String putS3(File uploadFile, String fileName) {
        s3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
            CannedAccessControlList.PublicRead
        ));

        return s3Client.getUrl(bucket, fileName).toString();
    }

    private void removeFile(File target) {
        if (target.delete()) {
            log.info("삭제");
        }else {
            log.info("삭제 실패");
        }
    }

    public Optional<File> convert(MultipartFile file) throws IOException {
        File c = new File(Objects.requireNonNull(file.getOriginalFilename()));

        if(c.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(c)) {
                fos.write(file.getBytes());
                return Optional.of(c);
            }
        }
        else {
            try{
                return Optional.of(new File(file.getOriginalFilename()));
            }catch (Exception e){}
        }
        return Optional.empty();
    }

}
