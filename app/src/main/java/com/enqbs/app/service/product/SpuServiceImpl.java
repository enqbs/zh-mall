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
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
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
    @Resource
    private ThreadPoolTaskExecutor executor;

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
    public List<ProductVO> getProductVOList(Integer categoryId, Integer limit) {
        List<Spu> spuList = spuMapper.selectListByCategoryIdAndLimit(categoryId, limit);
        return spuList.stream().map(s -> productConvert.spu2ProductVO(s)).collect(Collectors.toList());
    }

    @Override
    public List<ProductVO> getProductVOList(Set<Integer> spuIdSet) {
        List<Spu> spuList = spuMapper.selectListByIdSet(spuIdSet);

        if (CollectionUtils.isEmpty(spuList)) {
            return Collections.emptyList();
        }

        Map<Integer, List<SkuVO>> skuVOListMap = skuService.getSkuVOList(Collections.emptySet(), spuIdSet).stream()
                .collect(Collectors.groupingBy(SkuVO::getSpuId));
        return spuList.stream().map(s -> {
                    ProductVO productVO = productConvert.spu2ProductVO(s);
                    productVO.setSkuList(skuVOListMap.get(productVO.getId()));
                    return productVO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public void syncESProducts(String content) {
        ESSyncProducts syncProducts = new ESSyncProducts();
        JsonObject jsonObject = GsonUtil.json2Obj(content, JsonObject.class);

        executor.execute(() -> {
                    List<String> oldIds = new ArrayList<>();
                    jsonObject.getAsJsonArray("old").forEach(e -> {
                                if (ObjectUtils.isNotEmpty(e.getAsJsonObject().get("id"))) {
                                    oldIds.add(e.getAsJsonObject().get("id").getAsString());
                                }
                            }
                    );
                    syncProducts.setOldIds(oldIds);
                }
        );

        Set<Integer> spuIdSet = new HashSet<>();
        jsonObject.getAsJsonArray("data").forEach(e -> spuIdSet.add(e.getAsJsonObject().get("id").getAsInt()));
        syncProducts.setData(spuMapper.selectListByIdSet(spuIdSet));
        rabbitMQService.send(QueueEnum.ES_SYNC_PRODUCTS_QUEUE, syncProducts);
    }

}
