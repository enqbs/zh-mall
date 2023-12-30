package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.form.SpuSpecForm;
import com.enqbs.admin.vo.SpuSpecVO;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuSpecMapper;
import com.enqbs.generator.pojo.SpuSpec;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;

@Service
public class SpuSpecServiceImpl implements SpuSpecService {

    @Resource
    private SpuSpecMapper spuSpecMapper;

    @Resource
    private ProductConvert productConvert;

    @Override
    public SpuSpecVO getSpecVO(Integer spuId) {
        SpuSpec spec = spuSpecMapper.selectByPrimaryKey(spuId);
        SpuSpecVO specVO = productConvert.spec2SpecVO(spec);
        specVO.setPictures(StringUtils.isEmpty(spec.getPictures()) ?
                Collections.emptyList() : GsonUtil.json2ArrayList(spec.getPictures(), String[].class)
        );
        return specVO;
    }

    @Override
    public int insert(SpuSpecForm form) {
        SpuSpec spec = productConvert.form2Spec(form);
        spec.setPictures(CollectionUtils.isEmpty(form.getPictures()) ? null : GsonUtil.obj2Json(form.getPictures()));
        return spuSpecMapper.insertSelective(spec);
    }

    @Override
    public int update(SpuSpecForm form) {
        SpuSpec spec = productConvert.form2Spec(form);
        spec.setPictures(CollectionUtils.isEmpty(form.getPictures()) ? null : GsonUtil.obj2Json(form.getPictures()));
        return spuSpecMapper.updateByPrimaryKeySelective(spec);
    }

    @Override
    public int delete(Integer spuId) {
        return spuSpecMapper.deleteByPrimaryKey(spuId);
    }

}
