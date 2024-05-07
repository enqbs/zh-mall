package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.form.ProductOverviewForm;
import com.enqbs.admin.vo.SpuOverviewVO;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuOverviewMapper;
import com.enqbs.generator.pojo.SpuOverview;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;

@Service
public class SpuOverviewServiceImpl implements SpuOverviewService {

    @Resource
    private SpuOverviewMapper spuOverviewMapper;

    @Resource
    private ProductConvert productConvert;

    @Override
    public SpuOverviewVO getSpuOverviewVO(Integer spuId) {
        SpuOverview spuOverview = spuOverviewMapper.selectByPrimaryKey(spuId);
        SpuOverviewVO spuOverviewVO = productConvert.spuOverview2SpuOverviewVO(spuOverview);

        if (ObjectUtils.isNotEmpty(spuOverviewVO)) {
            spuOverviewVO.setPictures(StringUtils.isEmpty(spuOverview.getPictures()) ?
                    Collections.emptyList() : GsonUtil.json2ArrayList(spuOverview.getPictures(), String[].class)
            );
        }

        return spuOverviewVO;
    }

    @Override
    public int insert(ProductOverviewForm form) {
        SpuOverview spuOverview = productConvert.productOverviewForm2SpuOverview(form);
        spuOverview.setPictures(CollectionUtils.isEmpty(form.getPictures()) ?
                null : GsonUtil.obj2Json(form.getPictures())
        );
        return spuOverviewMapper.insertSelective(spuOverview);
    }

    @Override
    public int update(ProductOverviewForm form) {
        SpuOverview spuOverview = productConvert.productOverviewForm2SpuOverview(form);
        spuOverview.setPictures(CollectionUtils.isEmpty(form.getPictures()) ?
                null : GsonUtil.obj2Json(form.getPictures())
        );
        return spuOverviewMapper.updateByPrimaryKeySelective(spuOverview);
    }

    @Override
    public int delete(Integer spuId) {
        return spuOverviewMapper.deleteByPrimaryKey(spuId);
    }

}
