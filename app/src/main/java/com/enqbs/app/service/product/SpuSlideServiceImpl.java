package com.enqbs.app.service.product;

import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuSlideMapper;
import com.enqbs.generator.pojo.SpuSlide;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class SpuSlideServiceImpl implements SpuSlideService {

    @Resource
    private SpuSlideMapper spuSlideMapper;

    @Override
    public List<String> getSpuSlideList(Integer spuId) {
        SpuSlide spuSlide = spuSlideMapper.selectByPrimaryKey(spuId);
        return StringUtils.isEmpty(spuSlide.getPictures()) ?
                Collections.emptyList() :
                GsonUtil.json2ArrayList(spuSlide.getPictures(), String[].class);
    }

}
