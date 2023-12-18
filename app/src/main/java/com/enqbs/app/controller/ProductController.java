package com.enqbs.app.controller;

import com.enqbs.app.pojo.vo.ProductCommentReplyVO;
import com.enqbs.app.pojo.vo.ProductCommentVO;
import com.enqbs.app.service.product.*;
import com.enqbs.app.pojo.vo.ProductCategoryVO;
import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_HOME;
import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_LIST_HOME;
import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_LIST_HOME_LOCK;
import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_LIST_BANNER;
import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_LIST_BANNER_LOCK;
import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_NAVI;
import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_LIST_NAVI;
import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_LIST_NAVI_LOCK;
import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_LIST;
import static com.enqbs.common.constant.Constants.PRODUCT_CATEGORY_LIST_LOCK;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductCategoryService productCategoryService;
    @Resource
    private SpuService spuService;
    @Resource
    private SpuOverviewService spuOverviewService;
    @Resource
    private SpuSpecService spuSpecService;
    @Resource
    private ProductCommentService productCommentService;
    @Resource
    private ProductCommentReplyService productCommentReplyService;

    @GetMapping("/home")
    public R<Map<String, List<ProductCategoryVO>>> home() {
        /* 导航条商品分类列表 */
        List<ProductCategoryVO> naviCategoryList = productCategoryService
                .getCategoryVOList(PRODUCT_CATEGORY_LIST_NAVI, PRODUCT_CATEGORY_LIST_NAVI_LOCK,
                        null, PRODUCT_CATEGORY_NAVI, 6);
        /* 首页商品分类列表 */
        List<ProductCategoryVO> homeCategoryList = productCategoryService
                .getCategoryVOList(PRODUCT_CATEGORY_LIST_HOME, PRODUCT_CATEGORY_LIST_HOME_LOCK,
                        PRODUCT_CATEGORY_HOME, null, 24);
        /* banner 商品分类列表 */
        List<ProductCategoryVO> bannerCategoryList = productCategoryService
                .getCategoryVOList(PRODUCT_CATEGORY_LIST_BANNER, PRODUCT_CATEGORY_LIST_BANNER_LOCK,
                        null, null, 10);
        Map<String, List<ProductCategoryVO>> resultMap = new HashMap<>();
        resultMap.put("naviCategoryList", naviCategoryList);
        resultMap.put("homeCategoryList", homeCategoryList);
        resultMap.put("bannerCategoryList", bannerCategoryList);
        return R.ok(resultMap);
    }

    @GetMapping("/category/list")
    public R<List<ProductCategoryVO>> productCategoryList() {
        List<ProductCategoryVO> categoryList = productCategoryService
                .getCategoryVOList(PRODUCT_CATEGORY_LIST, PRODUCT_CATEGORY_LIST_LOCK, null, null, null);
        return R.ok(categoryList);
    }

    @GetMapping("/category/{categoryId}")
    public R<ProductCategoryVO> productCategory(@PathVariable Integer categoryId) {
        ProductCategoryVO category = productCategoryService.getCategoryVO(categoryId);
        return R.ok(category);
    }

    @GetMapping("/detail/{productId}")
    public R<ProductVO> productDetail(@PathVariable Integer productId) {
        ProductVO productInfo = spuService.getProductVO(productId);
        return R.ok(productInfo);
    }

    @GetMapping("/overview/{productId}")
    public R<Map<String, List<String>>> productOverview(@PathVariable Integer productId) {
        List<String> spuOverviewList = spuOverviewService.getSpuOverviewList(productId);
        Map<String, List<String>> resultMap = new HashMap<>();
        resultMap.put("overview", spuOverviewList);
        return R.ok(resultMap);
    }

    @GetMapping("/spec/{productId}")
    public R<Map<String, List<String>>> productSpec(@PathVariable Integer productId) {
        List<String> spuSpecList = spuSpecService.getSpuSpecList(productId);
        Map<String, List<String>> resultMap = new HashMap<>();
        resultMap.put("spec", spuSpecList);
        return R.ok(resultMap);
    }

    @GetMapping("/comment/list")
    public R<PageUtil<ProductCommentVO>> productCommentList(@RequestParam Integer productId,
                                                            @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<ProductCommentVO> pageProductCommentList = productCommentService.getProductCommentVOList(productId, sort,
                pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(pageProductCommentList);
    }

    @GetMapping("/comment/{commentId}")
    public R<ProductCommentVO> productCommentDetail(@PathVariable Integer commentId) {
        ProductCommentVO productCommentInfo = productCommentService.getProductCommentVO(commentId);
        return R.ok(productCommentInfo);
    }

    @GetMapping("/comment/reply/list")
    public R<PageUtil<ProductCommentReplyVO>> productCommentReplyList(@RequestParam Integer commentId,
                                                                      @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                                                      @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<ProductCommentReplyVO> pageProductCommentReplyList = productCommentReplyService
                .getProductCommentReplyVOList(commentId, sort, pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(pageProductCommentReplyList);
    }

}
