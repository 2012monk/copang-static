package com.example.copangstatic.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UploadForm {

    private Long detailId;

    private MultipartFile image;

}
