package com.enqbs.app.convert;

import com.enqbs.app.form.ProductCommentForm;
import com.enqbs.app.form.ProductCommentReplyForm;
import com.enqbs.app.pojo.vo.ProductCategoryVO;
import com.enqbs.app.pojo.vo.ProductCommentReplyVO;
import com.enqbs.app.pojo.vo.ProductCommentVO;
import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.app.pojo.vo.SkuVO;
import com.enqbs.generator.pojo.Spu;
import com.enqbs.generator.pojo.ProductCategory;
import com.enqbs.generator.pojo.ProductComment;
import com.enqbs.generator.pojo.ProductCommentReply;
import com.enqbs.generator.pojo.Sku;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductConvert {

    ProductCategoryVO category2CategoryVO(ProductCategory category);

    ProductVO spu2ProductVO(Spu spu);

    @Mapping(target = "params", ignore = true)
    SkuVO sku2SkuVO(Sku sku);

    @Mapping(target = "pictures", ignore = true)
    ProductComment productCommentForm2ProductComment(ProductCommentForm form);

    @Mapping(target = "pictures", ignore = true)
    ProductCommentVO productComment2ProductCommentVO(ProductComment productComment);

    ProductCommentReply productCommentReplyForm2ProductCommentReply(ProductCommentReplyForm form);

    ProductCommentReplyVO productCommentReply2ProductCommentReplyVO(ProductCommentReply productCommentReply);

}
