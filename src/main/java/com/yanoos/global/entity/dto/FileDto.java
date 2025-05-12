package com.yanoos.global.entity.dto;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Data
public class FileDto {
    private String url;
    private String path;
    private String extension;
    private MultiValueMap<String, String> parameters;
    private String name;

    private Map<String,String> headers;
    private ResponseEntity<byte[]> data;


    @Override
    public String toString(){
        return "FileDto{" +
                "url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", extension='" + extension + '\'' +
                ", parameters=" + parameters +
                ", name='" + name + '\'' +
                ", headers=" + headers +
                ", data=" + data +
                '}';
    }
}
