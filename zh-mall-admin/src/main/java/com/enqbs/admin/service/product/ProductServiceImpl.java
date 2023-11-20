package com.enqbs.admin.service.product;

import com.enqbs.admin.form.ProductForm;
import com.enqbs.admin.vo.ProductVO;
import com.enqbs.admin.vo.SkuStockVO;
import com.enqbs.admin.vo.SkuVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.ProductMapper;
import com.enqbs.generator.pojo.Product;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private SkuService skuService;

    @Resource
    private SkuStockService skuStockService;

    @Override
    public PageUtil<ProductVO> getProductVOList(Integer categoryId, Integer saleableStatus, Integer newStatus,
                                                Integer recommendStatus, Integer deleteStatus, SortEnum sortEnum,
                                                Integer pageNum, Integer pageSize) {
        PageUtil<ProductVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<ProductVO> productVOList = Collections.emptyList();
        List<Product> productList = productMapper.selectListByParam(categoryId, saleableStatus, newStatus, recommendStatus, deleteStatus, sortEnum.getSortType(), pageNum, pageSize);

        if (!CollectionUtils.isEmpty(productList)) {
            total = productMapper.countByParam(categoryId, saleableStatus, newStatus, recommendStatus, deleteStatus);
            Set<Integer> productIdSet = productList.stream().map(Product::getId).collect(Collectors.toSet());
            List<SkuVO> skuVOList = skuService.getSkuVOList(productIdSet);
            handleSkuVOListAndSkuStockVO(skuVOList);
            Map<Integer, List<SkuVO>> skuVOListMap = skuVOList.stream().collect(Collectors.groupingBy(SkuVO::getProductId));
            productVOList = productList.stream().map(e -> {
                ProductVO productVO = product2ProductVO(e);
                productVO.setSkuList(skuVOListMap.get(productVO.getId()));
                return productVO;
            }).collect(Collectors.toList());
        }

        pageUtil.setTotal(total);
        pageUtil.setList(productVOList);
        return pageUtil;
    }

    @Override
    public ProductVO getProductVO(Integer productId) {
        ProductVO productVO = new ProductVO();
        Product product = productMapper.selectByPrimaryKey(productId);

        if (ObjectUtils.isEmpty(product)) {
            return productVO;
        }

        List<SkuVO> skuVOList = skuService.getSkuVOList(productId);
        handleSkuVOListAndSkuStockVO(skuVOList);
        productVO = product2ProductVO(product);
        productVO.setSkuList(skuVOList);
        return productVO;
    }

    @Override
    public int insert(ProductForm form) {
        Product product = productForm2Product(form);
        return productMapper.insertSelective(product);
    }

    @Override
    public int update(Integer productId, ProductForm form) {
        Product product = productForm2Product(form);
        product.setId(productId);
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int delete(Integer productId) {
        Product product = new Product();
        product.setId(productId);
        product.setDeleteStatus(Constants.IS_DELETE);
        return productMapper.updateByPrimaryKeySelective(product);
    }

    private Product productForm2Product(ProductForm form) {
        Product product = new Product();
        BeanUtils.copyProperties(form, product);
        return product;
    }

    private ProductVO product2ProductVO(Product product) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product, productVO);
        return productVO;
    }

    private void handleSkuVOListAndSkuStockVO(List<SkuVO> skuVOList) {
        Set<Integer> skuIdSet = skuVOList.stream().map(SkuVO::getId).collect(Collectors.toSet());
        List<SkuStockVO> skuStockVOList = skuStockService.getSkuStockVOList(skuIdSet);
        Map<Integer, SkuStockVO> skuStockVOMap = skuStockVOList.stream().collect(Collectors.toMap(SkuStockVO::getSkuId, v -> v));
        skuVOList.forEach(skuVO -> skuVO.setSkuStock(skuStockVOMap.get(skuVO.getId())));
    }

}
