package com.enqbs.app.service.product;

import com.enqbs.app.convert.ProductConvert;
import com.enqbs.app.pojo.vo.SkuParamVO;
import com.enqbs.app.pojo.vo.SkuVO;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SkuMapper;
import com.enqbs.generator.pojo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkuServiceImpl implements SkuService {

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private ProductConvert productConvert;

    @Override
    public List<SkuVO> getSkuVOList(Integer spuId) {
        List<Sku> skuList = skuMapper.selectListBySpuId(spuId);
        return skuList.stream().map(s -> {
                    SkuVO skuVO = productConvert.sku2SkuVO(s);
                    skuVO.setParams(StringUtils.isEmpty(s.getParams()) ?
                            Collections.emptyList() : GsonUtil.json2ArrayList(s.getParams(), SkuParamVO[].class)
                    );
                    return skuVO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<SkuVO> getSkuVOList(Set<Integer> skuIdSet, Set<Integer> spuIdSet) {
        List<Sku> skuList = CollectionUtils.isEmpty(skuIdSet) && CollectionUtils.isEmpty(spuIdSet) ?
                Collections.emptyList() : CollectionUtils.isEmpty(skuIdSet) ?
                skuMapper.selectListBySpuIdSet(spuIdSet) : skuMapper.selectListByIdSet(skuIdSet);

        if (CollectionUtils.isEmpty(skuList)) {
            return Collections.emptyList();
        }

        return skuList.stream().map(s -> {
                    SkuVO skuVO = productConvert.sku2SkuVO(s);
                    skuVO.setParams(StringUtils.isEmpty(s.getParams()) ?
                            Collections.emptyList() : GsonUtil.json2ArrayList(s.getParams(), SkuParamVO[].class)
                    );
                    return skuVO;
                }
        ).collect(Collectors.toList());
    }

}
