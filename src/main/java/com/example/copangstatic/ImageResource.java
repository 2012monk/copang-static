package com.example.copangstatic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
public class ImageResource {

    @JsonProperty(access = Access.WRITE_ONLY)
    @Id @GeneratedValue
    private Long id;

    private String message;

    private Long uploaderId;

    private int imageHeight;

    private int imageWidth;

    private String savedName;

    private String originalFileName;

    private String uri;

    private String accessPoint;

    private String publicPath;

    @JsonIgnore
    private String uploadedUrl;

}
