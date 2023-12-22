package com.enqbs.admin.service.product;

import com.enqbs.admin.convert.ProductConvert;
import com.enqbs.admin.form.ProductCategoryForm;
import com.enqbs.admin.vo.ProductCategoryVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.ProductCategoryMapper;
import com.enqbs.generator.pojo.ProductCategory;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;

    @Resource
    private ProductConvert productConvert;

    @Override
    public PageUtil<ProductCategoryVO> getCategoryVOList(Integer parentId, Integer homeStatus, Integer naviStatus,
                                                         Integer deleteStatus, Integer pageNum, Integer pageSize) {
        PageUtil<ProductCategoryVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        List<ProductCategory> categoryList = productCategoryMapper.selectListByParam(parentId, homeStatus, naviStatus,
                deleteStatus, pageNum, pageSize);

        if (CollectionUtils.isEmpty(categoryList)) {
            return pageUtil;
        }

        Long total = productCategoryMapper.countByParam(parentId, homeStatus, naviStatus, deleteStatus);
        pageUtil.setTotal(total);
        pageUtil.setList(categoryList.stream().map(e -> productConvert.category2CategoryVO(e)).collect(Collectors.toList()));
        return pageUtil;
    }

    @Override
    public ProductCategoryVO getCategoryVO(Integer categoryId) {
        ProductCategory category = productCategoryMapper.selectByPrimaryKey(categoryId);
        return ObjectUtils.isEmpty(category) ? new ProductCategoryVO() : productConvert.category2CategoryVO(category);
    }

    @Override
    public int insert(ProductCategoryForm form) {
        ProductCategory category = productConvert.categoryForm2Category(form);
        return productCategoryMapper.insertSelective(category);
    }

    @Override
    public int update(Integer categoryId, ProductCategoryForm form) {
        ProductCategory category = productConvert.categoryForm2Category(form);
        category.setId(categoryId);
        return productCategoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int delete(Integer categoryId) {
        ProductCategory category = new ProductCategory();
        category.setId(categoryId);
        category.setDeleteStatus(Constants.IS_DELETE);
        return productCategoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int categoryOnAndOffHome(Integer categoryId) {
        List<Integer> limit = productCategoryMapper.upperLimitByHomeOrNavi(Constants.PRODUCT_CATEGORY_HOME, null);

        if (limit.size() >= 10) {
            throw new ServiceException("首页分类已上限");
        }

        ProductCategory category = productCategoryMapper.selectByPrimaryKey(categoryId);
        category.setNaviStatus(Constants.PRODUCT_CATEGORY_HOME.equals(category.getHomeStatus()) ?
                Constants.PRODUCT_CATEGORY_NOT_HOME : Constants.PRODUCT_CATEGORY_HOME
        );
        return productCategoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int categoryOnAndOffNavi(Integer categoryId) {
        List<Integer> limit = productCategoryMapper.upperLimitByHomeOrNavi(null, Constants.PRODUCT_CATEGORY_NAVI);

        if (limit.size() >= 7) {
            throw new ServiceException("导航栏分类已上限");
        }

        ProductCategory category = productCategoryMapper.selectByPrimaryKey(categoryId);
        category.setNaviStatus(Constants.PRODUCT_CATEGORY_NAVI.equals(category.getNaviStatus()) ?
                Constants.PRODUCT_CATEGORY_NOT_NAVI : Constants.PRODUCT_CATEGORY_NAVI
        );
        return productCategoryMapper.updateByPrimaryKeySelective(category);
    }

}
