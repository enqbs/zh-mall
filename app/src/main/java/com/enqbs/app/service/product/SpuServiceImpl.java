package com.enqbs.app.service.product;

import com.enqbs.app.convert.ProductConvert;
import com.enqbs.app.enums.QueueEnum;
import com.enqbs.app.pojo.ESSyncProducts;
import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.app.pojo.vo.SkuVO;
import com.enqbs.app.service.mq.RabbitMQService;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SpuMapper;
import com.enqbs.generator.pojo.Spu;
import com.google.gson.JsonObject;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    @Resource
    private RabbitMQService rabbitMQService;

    @Override
    public ProductVO getProductVO(Integer spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        ProductVO productVO = ObjectUtils.isEmpty(spu) || Constants.IS_DELETE.equals(spu.getDeleteStatus()) ? null : productConvert.spu2ProductVO(spu);

        if (ObjectUtils.isNotEmpty(productVO)) {
            productVO.setSlide(spuSlideService.getSlideList(spuId));
            productVO.setSkuList(skuService.getSkuVOList(spuId));
        }

        return productVO;
    }

    @Override
    public List<ProductVO> getProductVOList(Integer categoryId, Integer limit) {
        List<Spu> spuList = spuMapper.selectListByCategoryIdAndLimit(categoryId, limit);
        return spuList.stream().map(s -> productConvert.spu2ProductVO(s)).toList();
    }

    @Override
    public List<ProductVO> getProductVOList(Set<Integer> spuIdSet) {
        List<Spu> spuList = CollectionUtils.isEmpty(spuIdSet) ? Collections.emptyList() : spuMapper.selectListByIdSet(spuIdSet);
        Map<Integer, List<SkuVO>> skuVOListMap = skuService.getSkuVOList(Collections.emptySet(), spuIdSet).stream().collect(Collectors.groupingBy(SkuVO::getSpuId));
        return spuList.stream().map(s -> {
                    ProductVO productVO = productConvert.spu2ProductVO(s);
                    productVO.setSkuList(skuVOListMap.get(productVO.getId()));
                    return productVO;
                }
        ).toList();
    }

    @Override
    public void syncESProducts(String content) {
        ESSyncProducts syncProducts = new ESSyncProducts();
        JsonObject jsonObject = GsonUtil.json2Obj(content, JsonObject.class);

        Thread.ofVirtual().name("esProductsDeleteOldData").start(() -> {
                    List<String> oldIds = jsonObject.getAsJsonArray("old").asList().stream()
                            .filter(e -> ObjectUtils.isNotEmpty(e.getAsJsonObject().get("id")))
                            .map(e -> e.getAsJsonObject().get("id").getAsString()).toList();
                    syncProducts.setOldIds(oldIds);
                }
        );

        Set<Integer> spuIdSet = jsonObject.getAsJsonArray("data").asList().stream().map(e -> e.getAsJsonObject().get("id").getAsInt()).collect(Collectors.toSet());
        syncProducts.setData(spuMapper.selectListByIdSet(spuIdSet));
        rabbitMQService.send(QueueEnum.ES_SYNC_PRODUCTS_QUEUE, syncProducts);
    }

}
