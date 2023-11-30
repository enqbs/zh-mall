package com.enqbs.admin.controller;

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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/upload")
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("/spu-img")
    public R<Map<String, String>> uploadSpuImg(@RequestParam MultipartFile file) {
        String fileURL = fileService.getFileURL(file, DirEnum.SPU_IMG);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("spuImg", fileURL);
        return R.ok(resultMap);
    }

    @PostMapping("/spu-slider")
    public R<Map<String, List<String>>> uploadSpuSlider(@RequestParam List<MultipartFile> files) {
        List<String> fileURLList = files.stream()
                .map(e -> fileService.getFileURL(e, DirEnum.SPU_SLIDER)).collect(Collectors.toList());
        Map<String, List<String>> resultMap = new HashMap<>();
        resultMap.put("spuSlider", fileURLList);
        return R.ok(resultMap);
    }

    @PostMapping("/spu-spec")
    public R<Map<String, List<String>>> uploadSpuSpec(@RequestParam List<MultipartFile> files) {
        List<String> fileURLList = files.stream()
                .map(e -> fileService.getFileURL(e, DirEnum.SPU_SPEC)).collect(Collectors.toList());
        Map<String, List<String>> resultMap = new HashMap<>();
        resultMap.put("spuSpec", fileURLList);
        return R.ok(resultMap);
    }

    @PostMapping("/spu-overview")
    public R<Map<String, List<String>>> uploadSpuOverview(@RequestParam List<MultipartFile> files) {
        List<String> fileURLList = files.stream()
                .map(e -> fileService.getFileURL(e, DirEnum.SPU_OVERVIEW)).collect(Collectors.toList());
        Map<String, List<String>> resultMap = new HashMap<>();
        resultMap.put("spuOverview", fileURLList);
        return R.ok(resultMap);
    }

    @PostMapping("/sku-img")
    public R<Map<String, String>> uploadSkuImg(@RequestParam MultipartFile file) {
        String fileURL = fileService.getFileURL(file, DirEnum.SKU_IMG);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("skuImg", fileURL);
        return R.ok(resultMap);
    }

}