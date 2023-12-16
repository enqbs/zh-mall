package com.enqbs.admin.convert;

import com.enqbs.admin.form.ProductCategoryAttributeForm;
import com.enqbs.admin.form.ProductCategoryForm;
import com.enqbs.admin.form.ProductForm;
import com.enqbs.admin.form.ProductOverviewForm;
import com.enqbs.admin.form.ProductSlideForm;
import com.enqbs.admin.form.ProductSpecForm;
import com.enqbs.admin.form.SkuForm;
import com.enqbs.admin.vo.ProductCategoryAttributeVO;
import com.enqbs.admin.vo.ProductCategoryVO;
import com.enqbs.admin.vo.ProductVO;
import com.enqbs.admin.vo.SkuStockVO;
import com.enqbs.admin.vo.SkuVO;
import com.enqbs.admin.vo.SpuOverviewVO;
import com.enqbs.admin.vo.SpuSlideVO;
import com.enqbs.admin.vo.SpuSpecVO;
import com.enqbs.generator.pojo.ProductCategoryAttribute;
import com.enqbs.generator.pojo.Spu;
import com.enqbs.generator.pojo.ProductCategory;
import com.enqbs.generator.pojo.Sku;
import com.enqbs.generator.pojo.SkuStock;
import com.enqbs.generator.pojo.SpuOverview;
import com.enqbs.generator.pojo.SpuSlide;
import com.enqbs.generator.pojo.SpuSpec;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductConvert {

    ProductCategory categoryForm2Category(ProductCategoryForm form);

    ProductCategoryVO category2CategoryVO(ProductCategory category);

    ProductCategoryAttribute attributeForm2Attribute(ProductCategoryAttributeForm form);

    ProductCategoryAttributeVO attribute2AttributeVO(ProductCategoryAttribute categoryAttribute);

    Spu productForm2Product(ProductForm form);

    ProductVO spu2ProductVO(Spu spu);

    @Mapping(target = "params", ignore = true)
    Sku skuForm2Sku(SkuForm form);

    @Mapping(target = "params", ignore = true)
    SkuVO sku2SkuVO(Sku sku);

    SkuStockVO skuStock2SkuStockVO(SkuStock skuStock);

    @Mapping(target = "pictures", ignore = true)
    SpuOverview productOverviewForm2SpuOverview(ProductOverviewForm form);

    @Mapping(target = "pictures", ignore = true)
    SpuOverviewVO spuOverview2SpuOverviewVO(SpuOverview spuOverview);

    @Mapping(target = "pictures", ignore = true)
    SpuSpec productSpecForm2SpuSpec(ProductSpecForm form);

    @Mapping(target = "pictures", ignore = true)
    SpuSpecVO spuSpec2SpuSpecVO(SpuSpec spuSpec);

    @Mapping(target = "pictures", ignore = true)
    SpuSlide productSlideForm2SpuSlide(ProductSlideForm form);

    @Mapping(target = "pictures", ignore = true)
    SpuSlideVO spuSlide2SpuSlideVO(SpuSlide spuSlide);

}
