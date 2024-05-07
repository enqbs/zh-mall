package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.form.ProductSlideForm;
import com.enqbs.admin.vo.SpuSlideVO;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuSlideMapper;
import com.enqbs.generator.pojo.SpuSlide;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class SpuSlideServiceImpl implements SpuSlideService {

    @Resource
    private SpuSlideMapper spuSlideMapper;

    @Resource
    private ProductConvert productConvert;

    @Override
    public List<String> getSpuSlideList(Integer spuId) {
        SpuSlide spuSlide = spuSlideMapper.selectByPrimaryKey(spuId);
        return ObjectUtils.isEmpty(spuSlide) || StringUtils.isEmpty(spuSlide.getPictures()) ?
                Collections.emptyList() : GsonUtil.json2ArrayList(spuSlide.getPictures(), String[].class);
    }

    @Override
    public SpuSlideVO getSpuSlideVO(Integer spuId) {
        SpuSlide spuSlide = spuSlideMapper.selectByPrimaryKey(spuId);
        SpuSlideVO spuSlideVO = productConvert.spuSlide2SpuSlideVO(spuSlide);

        if (ObjectUtils.isNotEmpty(spuSlideVO)) {
            spuSlideVO.setPictures(StringUtils.isEmpty(spuSlide.getPictures()) ?
                    Collections.emptyList() : GsonUtil.json2ArrayList(spuSlide.getPictures(), String[].class)
            );
        }

        return spuSlideVO;
    }

    @Override
    public int insert(ProductSlideForm form) {
        SpuSlide spuSlide = productConvert.productSlideForm2SpuSlide(form);
        spuSlide.setPictures(CollectionUtils.isEmpty(form.getPictures()) ? null : GsonUtil.obj2Json(form.getPictures()));
        return spuSlideMapper.insertSelective(spuSlide);
    }

    @Override
    public int update(ProductSlideForm form) {
        SpuSlide spuSlide = productConvert.productSlideForm2SpuSlide(form);
        spuSlide.setPictures(CollectionUtils.isEmpty(form.getPictures()) ? null : GsonUtil.obj2Json(form.getPictures()));
        return spuSlideMapper.updateByPrimaryKeySelective(spuSlide);
    }

    @Override
    public int delete(Integer spuId) {
        return spuSlideMapper.deleteByPrimaryKey(spuId);
    }

}
