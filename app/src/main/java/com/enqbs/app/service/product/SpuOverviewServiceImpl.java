package com.enqbs.app.service.product;

import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuOverviewMapper;
import com.enqbs.generator.pojo.SpuOverview;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class SpuOverviewServiceImpl implements SpuOverviewService {

    @Resource
    private SpuOverviewMapper spuOverviewMapper;

    @Override
    public List<String> getSpuOverviewList(Integer spuId) {
        SpuOverview spuOverview = spuOverviewMapper.selectByPrimaryKey(spuId);
        return ObjectUtils.isEmpty(spuOverview) || StringUtils.isEmpty(spuOverview.getPictures()) ?
                Collections.emptyList() : GsonUtil.json2ArrayList(spuOverview.getPictures(), String[].class);
    }

}
