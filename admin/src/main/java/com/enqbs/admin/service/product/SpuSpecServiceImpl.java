package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.form.ProductSpecForm;
import com.enqbs.admin.vo.SpuSpecVO;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuSpecMapper;
import com.enqbs.generator.pojo.SpuSpec;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;

@Service
public class SpuSpecServiceImpl implements SpuSpecService {

    @Resource
    private SpuSpecMapper spuSpecMapper;

    @Resource
    private ProductConvert productConvert;

    @Override
    public SpuSpecVO getSpuSpecVO(Integer spuId) {
        SpuSpec spuSpec = spuSpecMapper.selectByPrimaryKey(spuId);

        if (ObjectUtils.isEmpty(spuSpec)) {
            return null;
        }

        SpuSpecVO spuSpecVO = productConvert.spuSpec2SpuSpecVO(spuSpec);
        spuSpecVO.setPictures(StringUtils.isEmpty(spuSpec.getPictures()) ?
                Collections.emptyList() : GsonUtil.json2ArrayList(spuSpec.getPictures(), String[].class)
        );
        return spuSpecVO;
    }

    @Override
    public int insert(ProductSpecForm form) {
        SpuSpec spuSpec = productConvert.productSpecForm2SpuSpec(form);
        spuSpec.setPictures(CollectionUtils.isEmpty(form.getPictures()) ? null : GsonUtil.obj2Json(form.getPictures()));
        return spuSpecMapper.insertSelective(spuSpec);
    }

    @Override
    public int update(ProductSpecForm form) {
        SpuSpec spuSpec = productConvert.productSpecForm2SpuSpec(form);
        spuSpec.setPictures(CollectionUtils.isEmpty(form.getPictures()) ? null : GsonUtil.obj2Json(form.getPictures()));
        return spuSpecMapper.updateByPrimaryKeySelective(spuSpec);
    }

    @Override
    public int delete(Integer spuId) {
        return spuSpecMapper.deleteByPrimaryKey(spuId);
    }

}
