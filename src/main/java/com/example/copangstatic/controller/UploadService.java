package com.example.copangstatic.controller;

import com.example.copangstatic.ImageResource;
import com.example.copangstatic.dto.UploadForm;
import com.example.copangstatic.utils.Uploader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UploadService {

    private final Uploader uploader;

    private final ImageResourceRepository repository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final String PRE_FIX = "image";
    public List<ImageResource> uploadFiles(List<UploadForm> files) {

        System.out.println("files == null = " + (files == null));
        List<ImageResource> resources = new ArrayList<>(files.size());

        for (UploadForm f : files) {
            try {
                ImageResource r = uploadAndGet(f.getImage(), PRE_FIX);
                repository.save(r);
                resources.add(r);
            } catch (Exception e) {
                resources.add(
                    ImageResource.builder()
                        .message("failed")
                        .build()
                );
                e.printStackTrace();
            }
        }
        return resources;
    }

    private ImageResource uploadAndGet(MultipartFile file, String dir)
        throws IOException {
        String fileName = file.getOriginalFilename();
        String serveName = UUID.randomUUID().toString();

        String uploadedUrl = uploader.upload(file, dir);

        return ImageResource.builder()
            .originalFileName(fileName)
            .uri(dir + "/" + serveName)
            .accessPoint(bucket)
            .publicPath("https://" + bucket + "/" + dir + "/" + serveName)
            .uploadedUrl(uploadedUrl)
            .savedName(serveName)
            .build();
    }

    private String makePath(String ...fileNames) {
        return String.join("/", fileNames);
    }

    private String makePath(Long detailId, Long itemId, Long sellerId) {
        StringJoiner joiner = new StringJoiner("/");

        joiner.add(itemId.toString());
        joiner.add(detailId.toString());
        joiner.add(sellerId.toString());
        return joiner.toString();
    }

    public ImageResource uploadFile(UploadForm form) throws IOException {

        MultipartFile file = form.getImage();

        ImageResource resource = uploadAndGet(file, PRE_FIX);

        repository.save(resource);
        return resource;
    }


}
