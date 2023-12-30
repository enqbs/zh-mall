package com.enqbs.app.service.product;

import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuSlideMapper;
import com.enqbs.generator.pojo.SpuSlide;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SpuSlideServiceImpl implements SpuSlideService {

    @Resource
    private SpuSlideMapper spuSlideMapper;

    @Override
    public List<String> getSlideList(Integer spuId) {
        SpuSlide slide = spuSlideMapper.selectByPrimaryKey(spuId);
        return StringUtils.isEmpty(slide.getPictures()) ?
                Collections.emptyList() : GsonUtil.json2ArrayList(slide.getPictures(), String[].class);
    }

}
