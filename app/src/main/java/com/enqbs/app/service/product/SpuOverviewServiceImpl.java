package com.enqbs.app.service.product;

import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuOverviewMapper;
import com.enqbs.generator.pojo.SpuOverview;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SpuOverviewServiceImpl implements SpuOverviewService {

    @Resource
    private SpuOverviewMapper spuOverviewMapper;

    @Override
    public List<String> getOverviewList(Integer spuId) {
        SpuOverview overview = spuOverviewMapper.selectByPrimaryKey(spuId);
        return ObjectUtils.isEmpty(overview) || StringUtils.isEmpty(overview.getPictures()) ?
                Collections.emptyList() : GsonUtil.json2ArrayList(overview.getPictures(), String[].class);
    }

}
