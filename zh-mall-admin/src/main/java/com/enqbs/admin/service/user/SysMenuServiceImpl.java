package com.enqbs.admin.service.user;

import com.enqbs.admin.form.SysMenuForm;
import com.enqbs.admin.vo.SysMenuVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.SysMenuMapper;
import com.enqbs.generator.pojo.SysMenu;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public Set<SysMenu> getSysMenuSet(String username) {
        return sysMenuMapper.selectSetByUsername(username);
    }

    @Override
    public List<SysMenuVO> getSysMenuVOList() {
        List<SysMenu> sysMenuList = sysMenuMapper.selectListByRoot();
        return sysMenuList.stream().map(this::sysMenu2SysMenuVO).collect(Collectors.toList());
    }

    @Override
    public PageUtil<SysMenuVO> getSysMenuVOList(Integer parentId, Integer roleId, Integer deleteStatus, Integer pageNum, Integer pageSize) {
        PageUtil<SysMenuVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<SysMenu> sysMenuList = sysMenuMapper.selectListByParam(parentId, roleId, deleteStatus, pageNum, pageSize);

        if (CollectionUtils.isEmpty(sysMenuList)) {
            pageUtil.setTotal(total);
            pageUtil.setList(Collections.emptyList());
            return pageUtil;
        }

        total = sysMenuMapper.countByParam(parentId, roleId, deleteStatus);
        List<SysMenuVO> sysMenuVOList = sysMenuList.stream().map(this::sysMenu2SysMenuVO).collect(Collectors.toList());
        pageUtil.setTotal(total);
        pageUtil.setList(sysMenuVOList);
        return pageUtil;
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public Future<List<SysMenuVO>> getSysMenuVOList(Integer roleId) {
        List<SysMenu> sysMenuList = sysMenuMapper.selectListByRoleId(roleId);
        List<SysMenuVO> sysMenuVOList = sysMenuList.stream().map(this::sysMenu2SysMenuVO).collect(Collectors.toList());
        return new AsyncResult<>(sysMenuVOList);
    }

    @Override
    public SysMenuVO getSysMenuVO(Integer id) {
        SysMenuVO sysMenuVO = new SysMenuVO();
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(id);

        if (ObjectUtils.isEmpty(sysMenu) || Constants.IS_DELETE.equals(sysMenu.getDeleteStatus())) {
            return sysMenuVO;
        }

        sysMenuVO = sysMenu2SysMenuVO(sysMenu);
        return sysMenuVO;
    }

    @Override
    public int insertSysMenu(SysMenuForm form) {
        SysMenu sysMenu = sysMenuForm2SysMenu(form);
        return sysMenuMapper.insertSelective(sysMenu);
    }

    @Override
    public int updateSysMenu(Integer id, SysMenuForm form) {
        SysMenu sysMenu = sysMenuForm2SysMenu(form);
        sysMenu.setId(id);
        return sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
    }

    @Override
    public int deleteSysMenu(Integer id) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        sysMenu.setDeleteStatus(Constants.IS_DELETE);
        return sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
    }

    private SysMenuVO sysMenu2SysMenuVO(SysMenu sysMenu) {
        SysMenuVO sysMenuVO = new SysMenuVO();
        BeanUtils.copyProperties(sysMenu, sysMenuVO);
        return sysMenuVO;
    }

    private SysMenu sysMenuForm2SysMenu(SysMenuForm form) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(form, sysMenu);
        return sysMenu;
    }

}
