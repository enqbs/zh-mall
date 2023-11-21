package com.enqbs.app.convert;

import com.enqbs.app.pojo.vo.ProductCategoryVO;
import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.app.pojo.vo.SkuVO;
import com.enqbs.generator.pojo.Product;
import com.enqbs.generator.pojo.ProductCategory;
import com.enqbs.generator.pojo.Sku;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductConvert {

    ProductCategoryVO productCategory2ProductCategoryVO(ProductCategory category);

    ProductVO product2ProductVO(Product product);

    SkuVO sku2SkuVO(Sku sku);

}
