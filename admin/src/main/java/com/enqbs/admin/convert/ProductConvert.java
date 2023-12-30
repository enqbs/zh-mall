package com.enqbs.admin.convert;

import com.enqbs.admin.form.ProductCategoryAttributeForm;
import com.enqbs.admin.form.ProductCategoryForm;
import com.enqbs.admin.form.ProductForm;
import com.enqbs.admin.form.SpuOverviewForm;
import com.enqbs.admin.form.SpuSlideForm;
import com.enqbs.admin.form.SpuSpecForm;
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
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductConvert {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "deleteStatus", ignore = true)
    })
    ProductCategory form2Category(ProductCategoryForm form);

    ProductCategoryVO category2CategoryVO(ProductCategory category);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "deleteStatus", ignore = true)
    })
    ProductCategoryAttribute form2Attribute(ProductCategoryAttributeForm form);

    ProductCategoryAttributeVO attribute2AttributeVO(ProductCategoryAttribute attribute);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "deleteStatus", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", ignore = true)
    })
    Spu form2Spu(ProductForm form);

    @Mappings({
            @Mapping(target = "slide", ignore = true),
            @Mapping(target = "skuList", ignore = true)
    })
    ProductVO spu2ProductVO(Spu spu);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "params", ignore = true),
            @Mapping(target = "saleableStatus", ignore = true),
            @Mapping(target = "deleteStatus", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", ignore = true)
    })
    Sku form2Sku(SkuForm form);

    @Mappings({
            @Mapping(target = "params", ignore = true),
            @Mapping(target = "stock", ignore = true)
    })
    SkuVO sku2SkuVO(Sku sku);

    SkuStockVO stock2StockVO(SkuStock stock);

    @Mapping(target = "pictures", ignore = true)
    SpuOverview form2Overview(SpuOverviewForm form);

    @Mapping(target = "pictures", ignore = true)
    SpuOverviewVO overview2OverviewVO(SpuOverview overview);

    @Mapping(target = "pictures", ignore = true)
    SpuSpec form2Spec(SpuSpecForm form);

    @Mapping(target = "pictures", ignore = true)
    SpuSpecVO spec2SpecVO(SpuSpec spec);

    @Mapping(target = "pictures", ignore = true)
    SpuSlide form2Slide(SpuSlideForm form);

    @Mapping(target = "pictures", ignore = true)
    SpuSlideVO slide2SlideVO(SpuSlide slide);

}
