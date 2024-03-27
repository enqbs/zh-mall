package com.enqbs.app.service.product;

import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuSpecMapper;
import com.enqbs.generator.pojo.SpuSpec;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SpuSpecServiceImpl implements SpuSpecService {

    @Resource
    private SpuSpecMapper spuSpecMapper;

    @Override
    public List<String> getSpecList(Integer spuId) {
        SpuSpec spec = spuSpecMapper.selectByPrimaryKey(spuId);
        return ObjectUtils.isEmpty(spec) || StringUtils.isEmpty(spec.getPictures()) ?
                Collections.emptyList() : GsonUtil.json2ArrayList(spec.getPictures(), String[].class);
    }

}
