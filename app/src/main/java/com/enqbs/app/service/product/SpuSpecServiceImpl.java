package com.enqbs.app.service.product;

import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuSpecMapper;
import com.enqbs.generator.pojo.SpuSpec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class SpuSpecServiceImpl implements SpuSpecService {

    @Resource
    private SpuSpecMapper spuSpecMapper;

    @Override
    public List<String> getSpuSpecList(Integer spuId) {
        SpuSpec spuSpec = spuSpecMapper.selectByPrimaryKey(spuId);
        return StringUtils.isEmpty(spuSpec.getPictures()) ?
                Collections.emptyList() :
                GsonUtil.json2ArrayList(spuSpec.getPictures(), String[].class);
    }

}
