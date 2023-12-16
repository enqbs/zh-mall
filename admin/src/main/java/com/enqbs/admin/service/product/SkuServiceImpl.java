package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.form.SkuForm;
import com.enqbs.admin.vo.SkuParamVO;
import com.enqbs.admin.vo.SkuVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.SkuMapper;
import com.enqbs.generator.pojo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private SkuStockService skuStockService;

    @Resource
    private ProductConvert productConvert;

    @Override
    public List<SkuVO> getSkuVOList(Set<Integer> spuIdSet) {
        List<Sku> skuList = skuMapper.selectListBySpuIdSet(spuIdSet);
        return skuList.stream().map(e -> {
                    SkuVO skuVO = productConvert.sku2SkuVO(e);
                    skuVO.setParams(StringUtils.isEmpty(e.getParams()) ?
                            Collections.emptyList() : GsonUtil.json2ArrayList(e.getParams(), SkuParamVO[].class)
                    );
                    return skuVO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<SkuVO> getSkuVOList(Integer spuId) {
        List<Sku> skuList = skuMapper.selectListBySpuId(spuId);
        return skuList.stream().map(e -> {
            SkuVO skuVO = productConvert.sku2SkuVO(e);
            skuVO.setParams(StringUtils.isEmpty(e.getParams()) ?
                    Collections.emptyList() : GsonUtil.json2ArrayList(e.getParams(), SkuParamVO[].class)
            );
            return skuVO;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(SkuForm form) {
        Sku sku = productConvert.skuForm2Sku(form);
        sku.setParams(GsonUtil.obj2Json(form.getParams()));
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
    @Transactional(rollbackFor = Exception.class)
    public void update(Integer skuId, SkuForm form) {
        Sku sku = productConvert.skuForm2Sku(form);
        sku.setId(skuId);
        sku.setParams(GsonUtil.obj2Json(form.getParams()));
        int row = skuMapper.updateByPrimaryKeySelective(sku);

        if (row <= 0) {
            throw new ServiceException("商品规格修改失败");
        }

        row = skuStockService.update(skuId, form.getStock());

        if (row <= 0) {
            throw new ServiceException("商品规格库存修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer skuId) {
        Sku sku = new Sku();
        sku.setId(skuId);
        sku.setDeleteStatus(Constants.IS_DELETE);
        int row = skuMapper.updateByPrimaryKeySelective(sku);

        if (row <= 0) {
            throw new ServiceException("商品规格删除失败");
        }

        row = skuStockService.delete(skuId);

        if (row <= 0) {
            throw new ServiceException("商品规格库存删除失败");
        }
    }

}