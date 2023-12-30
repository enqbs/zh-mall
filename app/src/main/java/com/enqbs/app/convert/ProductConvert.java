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
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductConvert {

    @Mappings({
            @Mapping(target = "categoryList", ignore = true),
            @Mapping(target = "productList", ignore = true)
    })
    ProductCategoryVO category2CategoryVO(ProductCategory category);

    @Mappings({
            @Mapping(target = "slide", ignore = true),
            @Mapping(target = "skuList", ignore = true)
    })
    ProductVO spu2ProductVO(Spu spu);

    @Mapping(target = "params", ignore = true)
    SkuVO sku2SkuVO(Sku sku);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "nickName", ignore = true),
            @Mapping(target = "photo", ignore = true),
            @Mapping(target = "pictures", ignore = true),
            @Mapping(target = "like", ignore = true),
            @Mapping(target = "deleteStatus", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", ignore = true)
    })
    ProductComment form2Comment(ProductCommentForm form);

    @Mappings({
            @Mapping(target = "pictures", ignore = true),
            @Mapping(target = "replyList", ignore = true)
    })
    ProductCommentVO comment2CommentVO(ProductComment comment);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "nickName", ignore = true),
            @Mapping(target = "photo", ignore = true),
            @Mapping(target = "like", ignore = true),
            @Mapping(target = "deleteStatus", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", ignore = true)
    })
    ProductCommentReply form2CommentReply(ProductCommentReplyForm form);

    ProductCommentReplyVO commentReply2CommentReplyVO(ProductCommentReply commentReply);

}
