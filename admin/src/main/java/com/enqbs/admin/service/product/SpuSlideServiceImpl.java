package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.form.SpuSlideForm;
import com.enqbs.admin.vo.SpuSlideVO;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuSlideMapper;
import com.enqbs.generator.pojo.SpuSlide;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class SpuSlideServiceImpl implements SpuSlideService {

    @Resource
    private SpuSlideMapper spuSlideMapper;

    @Resource
    private ProductConvert productConvert;

    @Override
    public List<String> getSlideList(Integer spuId) {
        SpuSlide slide = spuSlideMapper.selectByPrimaryKey(spuId);
        return ObjectUtils.isEmpty(slide) || StringUtils.isEmpty(slide.getPictures()) ?
                Collections.emptyList() : GsonUtil.json2ArrayList(slide.getPictures(), String[].class);
    }

    @Override
    public SpuSlideVO getSlideVO(Integer spuId) {
        SpuSlide slide = spuSlideMapper.selectByPrimaryKey(spuId);

        if (ObjectUtils.isEmpty(slide)) {
            return null;
        }

        SpuSlideVO slideVO = productConvert.slide2SlideVO(slide);
        slideVO.setPictures(StringUtils.isEmpty(slide.getPictures()) ?
                Collections.emptyList() : GsonUtil.json2ArrayList(slide.getPictures(), String[].class)
        );
        return slideVO;
    }

    @Override
    public int insert(SpuSlideForm form) {
        SpuSlide slide = productConvert.form2Slide(form);
        slide.setPictures(CollectionUtils.isEmpty(form.getPictures()) ? null : GsonUtil.obj2Json(form.getPictures()));
        return spuSlideMapper.insertSelective(slide);
    }

    @Override
    public int update(SpuSlideForm form) {
        SpuSlide slide = productConvert.form2Slide(form);
        slide.setPictures(CollectionUtils.isEmpty(form.getPictures()) ? null : GsonUtil.obj2Json(form.getPictures()));
        return spuSlideMapper.updateByPrimaryKeySelective(slide);
    }

    @Override
    public int delete(Integer spuId) {
        return spuSlideMapper.deleteByPrimaryKey(spuId);
    }

}
