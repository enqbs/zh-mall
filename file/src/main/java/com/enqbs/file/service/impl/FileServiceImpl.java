package com.enqbs.file.service.impl;

import com.aliyun.oss.OSS;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.IDUtil;
import com.enqbs.file.config.AliOSSConfig;
import com.enqbs.file.enums.DirEnum;
import com.enqbs.file.service.FileService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    private AliOSSConfig aliOSSConfig;

    @Override
    public String getFileURL(MultipartFile file, DirEnum dir) {
        String filename = file.getOriginalFilename();

        if (StringUtils.isEmpty(filename)) {
            throw new ServiceException("文件名不能为空");
        }

        String uuid = IDUtil.getUUID();
        String filetype = filename.substring(filename.lastIndexOf("."));
        String objectName = dir.getDesc() + uuid + filetype;

        OSS ossClient = aliOSSConfig.ossClient();

        try {
            ossClient.putObject(aliOSSConfig.getBucket(), objectName, file.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ObjectUtils.isNotEmpty(ossClient)) {
                ossClient.shutdown();
            }
        }

        return aliOSSConfig.getBucketDomain() + "/" + objectName;
    }

}
