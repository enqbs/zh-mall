package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.form.SkuForm;
import com.enqbs.admin.vo.SkuVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.SkuMapper;
import com.enqbs.generator.pojo.Sku;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkuServiceImpl implements SkuService {

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private SkuStockService skuStockService;

    @Resource
    private ProductConvert productConvert;

    @Override
    public List<SkuVO> getSkuVOList(Set<Integer> productIdSet) {
        List<Sku> skuList = skuMapper.selectListByProductIdSet(productIdSet);
        return skuList.stream().map(e -> productConvert.sku2SkuVO(e)).collect(Collectors.toList());
    }

    @Override
    public List<SkuVO> getSkuVOList(Integer productId) {
        List<Sku> skuList = skuMapper.selectListByProductId(productId);
        return skuList.stream().map(e -> productConvert.sku2SkuVO(e)).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(SkuForm form) {
        Sku sku = productConvert.skuForm2Sku(form);
        int row = skuMapper.insertSelective(sku);

        if (row <= 0) {
            throw new ServiceException("商品规格保存失败");
        }

        row = skuStockService.insert(sku.getId(), form.getStock());

        if (row <= 0) {
            throw new ServiceException("商品规格库存保存失败");
        }
    }

    @Override
    public void update(Integer skuId, SkuForm form) {
        Sku sku = productConvert.skuForm2Sku(form);
        sku.setId(skuId);
        int row = skuMapper.updateByPrimaryKeySelective(sku);

        if (row <= 0) {
            throw new ServiceException("商品规格更新失败");
        }

        row = skuStockService.update(skuId, form.getStock());

        if (row <= 0) {
            throw new ServiceException("商品规格库存更新失败");
        }
    }

    @Override
    public int delete(Integer skuId) {
        Sku sku = skuMapper.selectByPrimaryKey(skuId);
        sku.setDeleteStatus(Constants.IS_DELETE);
        return skuMapper.updateByPrimaryKeySelective(sku);
    }

}
