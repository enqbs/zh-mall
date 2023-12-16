package com.enqbs.admin.controller;

import com.enqbs.admin.form.SkuForm;
import com.enqbs.admin.service.product.SkuService;
import com.enqbs.common.util.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class SkuController {

    @Resource
    private SkuService skuService;

    @PostMapping("/sku")
    @PreAuthorize("hasAuthority('PRODUCT:ADD')")
    public R<Void> addSku(@Valid @RequestBody List<SkuForm> forms) {
        forms.forEach(form -> skuService.insert(form));
        return R.ok("商品规格保存成功");
    }

    @PutMapping("/sku/{skuId}")
    @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
    public R<Void> updateSku(@PathVariable Integer skuId, @Valid @RequestBody SkuForm form) {
        skuService.update(skuId, form);
        return R.ok("商品规格修改成功");
    }

    @DeleteMapping("/sku/{skuId}")
    @PreAuthorize("hasAuthority('PRODUCT:DELETE')")
    public R<Void> deleteSku(@PathVariable Integer skuId) {
        skuService.delete(skuId);
        return R.ok("商品规格删除成功");
    }

}
