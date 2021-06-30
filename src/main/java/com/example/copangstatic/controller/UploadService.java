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

    public List<ImageResource> uploadFiles(List<UploadForm> files, Long itemId) {

        System.out.println("files == null = " + (files == null));
        List<ImageResource> resources = new ArrayList<>(files.size());

        for (UploadForm f : files) {
            try {
                ImageResource r = uploadAndGet(f.getImage(), f.getDetailId(), itemId);
                repository.save(r);
                resources.add(r);
            } catch (Exception e) {
                resources.add(
                    ImageResource.builder()
                        .message("failed")
                        .itemId(itemId)
                        .itemDetailId(f.getDetailId())
                        .build()
                );
                e.printStackTrace();
            }
        }
        return resources;
    }

    private ImageResource uploadAndGet(MultipartFile file, Long detailId, Long itemId)
        throws IOException {
        String fileName = file.getOriginalFilename();
        String serveName = UUID.randomUUID().toString();

        String dirName = makePath(itemId, detailId, serveName);

        String uploadedUrl = uploader.upload(file, dirName);

        return ImageResource.builder()
            .itemDetailId(detailId)
            .itemId(itemId)
            .originalFileName(fileName)
            .uri(dirName)
            .accessPoint(bucket)
            .publicPath("https://" + bucket + "/" + dirName)
            .uploadedUrl(uploadedUrl)
            .savedName(serveName)
            .build();
    }

    private String makePath(Long itemId, Long detailId, String fileName) {
        StringJoiner joiner = new StringJoiner("/");

        joiner.add(itemId.toString());
        joiner.add(detailId.toString());
        joiner.add(fileName);
        return joiner.toString();
    }

    private String makePath(Long detailId, Long itemId, Long sellerId) {
        StringJoiner joiner = new StringJoiner("/");

        joiner.add(itemId.toString());
        joiner.add(detailId.toString());
        joiner.add(sellerId.toString());
        return joiner.toString();
    }

    public ImageResource uploadFile(UploadForm form, Long itemId) throws IOException {

        MultipartFile file = form.getImage();
        Long detailId = form.getDetailId();

        ImageResource resource = uploadAndGet(file, detailId, itemId);

        repository.save(resource);
        return resource;
    }


}
