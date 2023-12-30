package com.enqbs.admin.controller;

import com.enqbs.admin.form.SysMenuForm;
import com.enqbs.admin.service.sys.MenuService;
import com.enqbs.admin.vo.SysMenuVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/root-list")
    public R<List<SysMenuVO>> menuRootList() {
        return R.ok(menuService.getMenuVOList());
    }

    @GetMapping("/list")
    public R<PageUtil<SysMenuVO>> menuPage(@RequestParam(required = false) Integer parentId,
                                           @RequestParam(required = false) Integer roleId,
                                           @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<SysMenuVO> menuVOPage = menuService.menuVOPage(parentId, roleId, deleteStatus,
                pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(menuVOPage);
    }

    @GetMapping("/{menuId}")
    public R<SysMenuVO> menuDetail(@PathVariable Integer menuId) {
        return R.ok(menuService.getMenuVO(menuId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SYS_MENU:ADD')")
    public R<Void> addMenu(@Valid @RequestBody SysMenuForm form) {
        int row = menuService.insert(form);

        if (row <= 0) {
            throw new ServiceException("菜单新增失败");
        }

        return R.ok("菜单新增成功");
    }

    @PutMapping("/{menuId}")
    @PreAuthorize("hasAuthority('SYS_MENU:UPDATE')")
    public R<Void> updateMenu(@PathVariable Integer menuId, @Valid @RequestBody SysMenuForm form) {
        int row = menuService.update(menuId, form);

        if (row <= 0) {
            throw new ServiceException("菜单修改失败");
        }

        return R.ok("菜单修改成功");
    }

    @DeleteMapping("/{menuId}")
    @PreAuthorize("hasAuthority('SYS_MENU:DELETE')")
    public R<Void> deleteMenu(@PathVariable Integer menuId) {
        int row = menuService.delete(menuId);

        if (row <= 0) {
            throw new ServiceException("菜单删除失败");
        }

        return R.ok("菜单删除成功");
    }

}
