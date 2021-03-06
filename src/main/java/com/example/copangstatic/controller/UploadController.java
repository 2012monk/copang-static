package com.example.copangstatic.controller;

import com.example.copangstatic.ImageResource;
import com.example.copangstatic.dto.ResponseMessage;
import com.example.copangstatic.dto.UploadForm;
import com.example.copangstatic.dto.UploadListForm;
import com.example.copangstatic.utils.Uploader;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UploadController {

    private final Uploader uploader;

    private final UploadService service;

//    @Value("${cloud.aws.credentials.accessKey}")
//    private String key;
//
//    @GetMapping("/1")
//    public String get() {
//        return key;
//    }

    @PostMapping("/upload/image")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        return uploader.upload(file, "static");
    }

    @PostMapping("/upload")
    public ResponseMessage<ImageResource> upload(@ModelAttribute UploadForm form) throws IOException {
        ImageResource resource = service.uploadFile(form);

        return ResponseMessage.of(
            resource
        );
    }

    @PostMapping("/upload/list}")
    public ResponseMessage<List<ImageResource>> uploadImage(@ModelAttribute UploadListForm forms) {
        List<ImageResource> res = service.uploadFiles(forms.getImageList());
        return ResponseMessage.of(res);
    }

}
