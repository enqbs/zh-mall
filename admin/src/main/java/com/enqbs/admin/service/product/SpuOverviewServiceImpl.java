package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.form.SpuOverviewForm;
import com.enqbs.admin.vo.SpuOverviewVO;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuOverviewMapper;
import com.enqbs.generator.pojo.SpuOverview;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;

@Service
public class SpuOverviewServiceImpl implements SpuOverviewService {

    @Resource
    private SpuOverviewMapper spuOverviewMapper;

    @Resource
    private ProductConvert productConvert;

    @Override
    public SpuOverviewVO getOverviewVO(Integer spuId) {
        SpuOverview overview = spuOverviewMapper.selectByPrimaryKey(spuId);

        if (ObjectUtils.isEmpty(overview)) {
            return null;
        }

        SpuOverviewVO overviewVO = productConvert.overview2OverviewVO(overview);
        overviewVO.setPictures(StringUtils.isEmpty(overview.getPictures()) ?
                Collections.emptyList() : GsonUtil.json2ArrayList(overview.getPictures(), String[].class)
        );
        return overviewVO;
    }

    @Override
    public int insert(SpuOverviewForm form) {
        SpuOverview overview = productConvert.form2Overview(form);
        overview.setPictures(CollectionUtils.isEmpty(form.getPictures()) ? null : GsonUtil.obj2Json(form.getPictures()));
        return spuOverviewMapper.insertSelective(overview);
    }

    @Override
    public int update(SpuOverviewForm form) {
        SpuOverview overview = productConvert.form2Overview(form);
        overview.setPictures(CollectionUtils.isEmpty(form.getPictures()) ? null : GsonUtil.obj2Json(form.getPictures()));
        return spuOverviewMapper.updateByPrimaryKeySelective(overview);
    }

    @Override
    public int delete(Integer spuId) {
        return spuOverviewMapper.deleteByPrimaryKey(spuId);
    }

}
