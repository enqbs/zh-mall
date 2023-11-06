package com.enqbs.admin.service.product;

import com.enqbs.admin.form.ProductForm;
import com.enqbs.admin.form.SkuForm;
import com.enqbs.admin.vo.ProductVO;
import com.enqbs.admin.vo.SkuStockVO;
import com.enqbs.admin.vo.SkuVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.ProductMapper;
import com.enqbs.generator.dao.SkuMapper;
import com.enqbs.generator.dao.SkuStockMapper;
import com.enqbs.generator.pojo.Product;
import com.enqbs.generator.pojo.Sku;
import com.enqbs.generator.pojo.SkuStock;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private SkuStockMapper skuStockMapper;

    @Override
    public PageUtil<ProductVO> getProductVOList(Integer categoryId, Integer saleableStatus, Integer newStatus,
                                                Integer recommendStatus, Integer deleteStatus, Integer pageNum, Integer pageSize) {
        PageUtil<ProductVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<ProductVO> productVOList = new ArrayList<>();
        List<Product> productList = productMapper.selectListByParam(categoryId, saleableStatus, newStatus, recommendStatus, deleteStatus, pageNum, pageSize);

        if (CollectionUtils.isEmpty(productList)) {
            pageUtil.setTotal(total);
            pageUtil.setList(productVOList);
            return pageUtil;
        }

        total = productMapper.countByParam(categoryId, saleableStatus, newStatus, recommendStatus, deleteStatus);
        Set<Integer> productIdSet = productList.stream().map(Product::getId).collect(Collectors.toSet());
        List<SkuVO> skuVOList = getSkuVOList(productIdSet);
        handleSkuVOListAndSkuStockVO(skuVOList);
        Map<Integer, List<SkuVO>> skuVOListMap = skuVOList.stream().collect(Collectors.groupingBy(SkuVO::getProductId));
        productList.stream().map(this::product2ProductVO).collect(Collectors.toList()).forEach(productVO -> {
            productVO.setSkuList(skuVOListMap.get(productVO.getId()));
            productVOList.add(productVO);
        });
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

        List<SkuVO> skuVOList = getSkuVOList(productId);
        handleSkuVOListAndSkuStockVO(skuVOList);
        productVO = product2ProductVO(product);
        productVO.setSkuList(skuVOList);
        return productVO;
    }

    @Override
    public int insertProduct(ProductForm form) {
        Product product = productForm2Product(form);
        return productMapper.insertSelective(product);
    }

    @Override
    public int updateProduct(Integer productId, ProductForm form) {
        Product product = productForm2Product(form);
        product.setId(productId);
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int deleteProduct(Integer productId) {
        Product product = new Product();
        product.setId(productId);
        product.setDeleteStatus(Constants.IS_DELETE);
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSku(SkuForm form) {
        int row;
        Sku sku = skuForm2Sku(form);
        row = skuMapper.insertSelective(sku);

        if (row <= 0) {
            throw new ServiceException("商品规格保存失败");
        }

        SkuStock skuStock = new SkuStock();
        skuStock.setSkuId(sku.getId());
        skuStock.setActualStock(form.getStock());
        skuStock.setStock(form.getStock());
        row = skuStockMapper.insertSelective(skuStock);

        if (row <= 0) {
            throw new ServiceException("商品规格库存保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSku(Integer skuId, SkuForm form) {
        int row;
        Sku sku = skuForm2Sku(form);
        sku.setId(skuId);
        row = skuMapper.updateByPrimaryKeySelective(sku);

        if (row <= 0) {
            throw new ServiceException("商品规格更新失败");
        }

        SkuStock skuStock = new SkuStock();
        skuStock.setSkuId(skuId);
        skuStock.setActualStock(form.getStock());
        skuStock.setStock(form.getStock());
        row = skuStockMapper.updateByPrimaryKeySelective(skuStock);

        if (row <= 0) {
            throw new ServiceException("商品规格库存更新失败");
        }
    }

    @Override
    public int deleteSku(Integer skuId) {
        Sku sku = new Sku();
        sku.setId(skuId);
        sku.setDeleteStatus(Constants.IS_DELETE);
        return skuMapper.updateByPrimaryKeySelective(sku);
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

    private List<SkuVO> getSkuVOList(Set<Integer> productIdSet) {
        List<Sku> skuList = skuMapper.selectListByProductIdSet(productIdSet);
        return skuList.stream().map(this::sku2SkuVO).collect(Collectors.toList());
    }

    private List<SkuVO> getSkuVOList(Integer productId) {
        List<Sku> skuList = skuMapper.selectListByProductId(productId);
        return skuList.stream().map(this::sku2SkuVO).collect(Collectors.toList());
    }

    private List<SkuStockVO> getSkuStockVOList(Set<Integer> skuIdSet) {
        List<SkuStock> skuStockList = skuStockMapper.selectListBySkuIdSet(skuIdSet);
        return skuStockList.stream().map(this::skuStock2SkuStockVO).collect(Collectors.toList());
    }

    private Sku skuForm2Sku(SkuForm form) {
        Sku sku = new Sku();
        BeanUtils.copyProperties(form, sku);
        return sku;
    }

    private SkuVO sku2SkuVO(Sku sku) {
        SkuVO skuVO = new SkuVO();
        BeanUtils.copyProperties(sku, skuVO);
        return skuVO;
    }

    private SkuStockVO skuStock2SkuStockVO(SkuStock skuStock) {
        SkuStockVO skuStockVO = new SkuStockVO();
        BeanUtils.copyProperties(skuStock, skuStockVO);
        return skuStockVO;
    }

    private void handleSkuVOListAndSkuStockVO(List<SkuVO> skuVOList) {
        Set<Integer> skuIdSet = skuVOList.stream().map(SkuVO::getId).collect(Collectors.toSet());
        List<SkuStockVO> skuStockVOList = getSkuStockVOList(skuIdSet);
        Map<Integer, SkuStockVO> skuStockVOMap = skuStockVOList.stream().collect(Collectors.toMap(SkuStockVO::getSkuId, skuStockVO -> skuStockVO));
        skuVOList.forEach(skuVO -> skuVO.setSkuStock(skuStockVOMap.get(skuVO.getId())));
    }

}
