package com.enqbs.file.service;

import com.enqbs.file.enums.DirEnum;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String getFileURL(MultipartFile file, DirEnum dir);

}
