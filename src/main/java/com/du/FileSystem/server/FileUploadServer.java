package com.du.FileSystem.server;

import org.springframework.web.multipart.MultipartFile;


public interface FileUploadServer {
    boolean fileUploader(MultipartFile file);
}
