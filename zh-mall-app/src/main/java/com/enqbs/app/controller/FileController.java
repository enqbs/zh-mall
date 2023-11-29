package com.enqbs.app.controller;

import com.enqbs.common.util.R;
import com.enqbs.file.enums.DirEnum;
import com.enqbs.file.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("/user-photo")
    public R<Map<String, String>> uploadUserPhoto(@RequestParam MultipartFile file) {
        String fileURL = fileService.getFileURL(file, DirEnum.USER_PHOTO);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("photo", fileURL);
        return R.ok(resultMap);
    }

}
