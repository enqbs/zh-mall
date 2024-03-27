package com.enqbs.admin.service.user;

import com.enqbs.admin.convert.SysUserConvert;
import com.enqbs.admin.form.SysMenuForm;
import com.enqbs.admin.vo.SysMenuVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.SysMenuMapper;
import com.enqbs.generator.pojo.SysMenu;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysUserConvert sysUserConvert;

    @Override
    public Set<SysMenu> getSysMenuSet(String username) {
        return sysMenuMapper.selectSetByUsername(username);
    }

    @Override
    public List<SysMenuVO> getSysMenuVOList() {
        List<SysMenu> sysMenuList = sysMenuMapper.selectListByRoot();
        return CollectionUtils.isEmpty(sysMenuList) ?
                Collections.emptyList() : sysMenuList.stream().map(m -> sysUserConvert.sysMenu2SysMenuVO(m)).collect(Collectors.toList());
    }

    @Override
    public PageUtil<SysMenuVO> sysMenuVOListPage(Integer parentId, Integer roleId,
                                                 Integer deleteStatus, Integer pageNum, Integer pageSize) {
        Long total = sysMenuMapper.countByParam(parentId, roleId, deleteStatus);
        List<SysMenu> sysMenuList = sysMenuMapper.selectListByParam(parentId, roleId, deleteStatus, pageNum, pageSize);
        PageUtil<SysMenuVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        pageUtil.setTotal(total);
        pageUtil.setList(sysMenuList.stream().map(m -> sysUserConvert.sysMenu2SysMenuVO(m)).collect(Collectors.toList()));
        return pageUtil;
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public Future<List<SysMenuVO>> getSysMenuVOList(Integer roleId) {
        List<SysMenu> sysMenuList = sysMenuMapper.selectListByRoleId(roleId);
        return CompletableFuture.completedFuture(sysMenuList.stream()
                .map(m -> sysUserConvert.sysMenu2SysMenuVO(m)).collect(Collectors.toList())
        );
    }

    @Override
    public SysMenuVO getSysMenuVO(Integer id) {
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(id);
        return ObjectUtils.isEmpty(sysMenu) || Constants.IS_DELETE.equals(sysMenu.getDeleteStatus()) ? null : sysUserConvert.sysMenu2SysMenuVO(sysMenu);
    }

    @Override
    public int insert(SysMenuForm form) {
        SysMenu sysMenu = sysUserConvert.sysMenuForm2SysMenu(form);
        return sysMenuMapper.insertSelective(sysMenu);
    }

    @Override
    public int update(Integer id, SysMenuForm form) {
        SysMenu sysMenu = sysUserConvert.sysMenuForm2SysMenu(form);
        sysMenu.setId(id);
        return sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
    }

    @Override
    public int delete(Integer id) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        sysMenu.setDeleteStatus(Constants.IS_DELETE);
        return sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
    }

}
