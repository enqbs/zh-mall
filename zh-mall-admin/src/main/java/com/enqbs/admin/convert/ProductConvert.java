package com.enqbs.admin.convert;

import com.enqbs.admin.form.ProductCategoryForm;
import com.enqbs.admin.form.ProductForm;
import com.enqbs.admin.form.SkuForm;
import com.enqbs.admin.vo.ProductCategoryVO;
import com.enqbs.admin.vo.ProductVO;
import com.enqbs.admin.vo.SkuStockVO;
import com.enqbs.admin.vo.SkuVO;
import com.enqbs.generator.pojo.Product;
import com.enqbs.generator.pojo.ProductCategory;
import com.enqbs.generator.pojo.Sku;
import com.enqbs.generator.pojo.SkuStock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductConvert {

    ProductCategory productCategoryForm2ProductCategory(ProductCategoryForm form);

    ProductCategoryVO productCategory2ProductCategoryVO(ProductCategory productCategory);

    Product productForm2Product(ProductForm form);

    ProductVO product2ProductVO(Product product);

    Sku skuForm2Sku(SkuForm form);

    SkuVO sku2SkuVO(Sku sku);

    SkuStockVO skuStock2SkuStockVO(SkuStock skuStock);

}
