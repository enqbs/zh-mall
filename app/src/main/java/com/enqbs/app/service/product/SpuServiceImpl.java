package com.enqbs.app.service.product;

import com.enqbs.app.convert.ProductConvert;
import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.app.pojo.vo.SkuVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.generator.dao.SpuMapper;
import com.enqbs.generator.pojo.Spu;
import com.enqbs.search.pojo.ESProduct;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SpuServiceImpl implements SpuService {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private SpuSlideService spuSlideService;

    @Resource
    private SkuService skuService;

    @Resource
    private ProductConvert productConvert;

    @Override
    public ProductVO getProductVO(Integer spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);

        if (ObjectUtils.isEmpty(spu) || Constants.IS_DELETE.equals(spu.getDeleteStatus())) {
            return new ProductVO();
        }

        ProductVO productVO = productConvert.spu2ProductVO(spu);
        productVO.setSlide(spuSlideService.getSpuSlideList(spuId));
        productVO.setSkuList(skuService.getSkuVOList(spuId));
        return productVO;
    }

    @Override
    public ESProduct getESProduct(Integer spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);

        if (ObjectUtils.isEmpty(spu) || Constants.IS_DELETE.equals(spu.getDeleteStatus())) {
            return new ESProduct();
        }

        return productConvert.spu2ESProduct(spu);
    }

    @Override
    public List<ProductVO> getProductVOList(Integer categoryId, Integer limit) {
        List<Spu> spuList = spuMapper.selectListByCategoryIdAndLimit(categoryId, limit);
        return spuList.stream().map(e -> productConvert.spu2ProductVO(e)).collect(Collectors.toList());
    }

    @Override
    public List<ProductVO> getProductVOList(Set<Integer> spuIdSet) {
        List<Spu> spuList = spuMapper.selectListByIdSet(spuIdSet);

        if (CollectionUtils.isEmpty(spuList)) {
            return Collections.emptyList();
        }

        Map<Integer, List<SkuVO>> skuVOListMap = skuService.getSkuVOList(Collections.emptySet(), spuIdSet).stream()
                .collect(Collectors.groupingBy(SkuVO::getSpuId));
        return spuList.stream().map(e -> {
                    ProductVO productVO = productConvert.spu2ProductVO(e);
                    productVO.setSkuList(skuVOListMap.get(productVO.getId()));
                    return productVO;
                }
        ).collect(Collectors.toList());
    }

}
