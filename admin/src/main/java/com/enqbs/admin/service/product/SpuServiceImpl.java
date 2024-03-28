package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.ProductForm;
import com.enqbs.admin.vo.ProductVO;
import com.enqbs.admin.vo.SkuStockVO;
import com.enqbs.admin.vo.SkuVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.SpuMapper;
import com.enqbs.generator.pojo.Spu;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

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
    private SkuStockService skuStockService;
    @Resource
    private ProductConvert productConvert;

    @Override
    public PageUtil<ProductVO> productVOListPage(Integer categoryId, Integer saleableStatus, Integer newStatus, Integer recommendStatus,
                                                 Integer deleteStatus, SortEnum sort, Integer pageNum, Integer pageSize) {
        Long total = spuMapper.countByParam(categoryId, saleableStatus, newStatus, recommendStatus, deleteStatus);
        List<Spu> spuList = spuMapper.selectListByParam(categoryId, saleableStatus, newStatus, recommendStatus, deleteStatus, sort.getSortType(), pageNum, pageSize);
        Set<Integer> spuIdSet = spuList.stream().map(Spu::getId).collect(Collectors.toSet());
        List<SkuVO> skuVOList = skuService.getSkuVOList(spuIdSet);
        handleSkuVOListAndStockVO(skuVOList);
        Map<Integer, List<SkuVO>> skuVOListMap = skuVOList.stream().collect(Collectors.groupingBy(SkuVO::getSpuId));
        List<ProductVO> productVOList = spuList.stream().map(s -> {
                    ProductVO productVO = productConvert.spu2ProductVO(s);
                    productVO.setSlide(spuSlideService.getSlideList(productVO.getId()));
                    productVO.setSkuList(skuVOListMap.get(productVO.getId()));
                    return productVO;
                }
        ).toList();
        PageUtil<ProductVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        pageUtil.setTotal(total);
        pageUtil.setList(productVOList);
        return pageUtil;
    }

    @Override
    public ProductVO getProductVO(Integer spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);

        if (ObjectUtils.isEmpty(spu)) {
            return null;
        }

        List<String> slideList = spuSlideService.getSlideList(spuId);
        List<SkuVO> skuVOList = skuService.getSkuVOList(spuId);
        handleSkuVOListAndStockVO(skuVOList);
        ProductVO productVO = productConvert.spu2ProductVO(spu);
        productVO.setSlide(slideList);
        productVO.setSkuList(skuVOList);
        return productVO;
    }

    @Override
    public int insert(ProductForm form) {
        Spu spu = productConvert.form2Spu(form);
        return spuMapper.insertSelective(spu);
    }

    @Override
    public int update(Integer productId, ProductForm form) {
        Spu spu = productConvert.form2Spu(form);
        spu.setId(productId);
        return spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public int delete(Integer spuId) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setDeleteStatus(Constants.IS_DELETE);
        return spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public int spuOnAndOffTheShelves(Integer spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        spu.setSaleableStatus(Constants.PRODUCT_SHELVES.equals(spu.getSaleableStatus()) ?
                Constants.PRODUCT_NOT_SHELVES : Constants.PRODUCT_SHELVES
        );
        return spuMapper.updateByPrimaryKeySelective(spu);
    }

    private void handleSkuVOListAndStockVO(List<SkuVO> skuVOList) {
        Set<Integer> skuIdSet = skuVOList.stream().map(SkuVO::getId).collect(Collectors.toSet());
        Map<Integer, SkuStockVO> stockVOMap = skuStockService.getStockVOList(skuIdSet).stream().collect(Collectors.toMap(SkuStockVO::getSkuId, v -> v));
        skuVOList.forEach(svo -> svo.setStock(stockVOMap.get(svo.getId())));
    }

}
