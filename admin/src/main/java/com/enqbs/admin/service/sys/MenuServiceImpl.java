package com.enqbs.admin.service.sys;

import com.enqbs.admin.convert.SysConvert;
import com.enqbs.admin.form.SysMenuForm;
import com.enqbs.admin.vo.SysMenuVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.SysMenuMapper;
import com.enqbs.generator.pojo.SysMenu;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysConvert sysConvert;

    @Override
    public PageUtil<SysMenuVO> menuVOPage(Integer parentId, Integer roleId, Integer deleteStatus, Integer pageNum, Integer pageSize) {
        PageUtil<SysMenuVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        List<SysMenu> menuList = sysMenuMapper.selectListByParam(parentId, roleId, deleteStatus, pageNum, pageSize);

        if (CollectionUtils.isEmpty(menuList)) {
            return pageUtil;
        }

        Long total = sysMenuMapper.countByParam(parentId, roleId, deleteStatus);
        pageUtil.setTotal(total);
        pageUtil.setList(menuList.stream().map(m -> sysConvert.menu2MenuVO(m)).toList());
        return pageUtil;
    }

    @Override
    public Set<SysMenu> getMenuSet(String username) {
        return sysMenuMapper.selectSetByUsername(username);
    }

    @Override
    public List<SysMenuVO> getMenuVOList() {
        List<SysMenu> menuList = sysMenuMapper.selectListByRoot();
        return menuList.stream().map(m -> sysConvert.menu2MenuVO(m)).toList();
    }

    @Override
    public List<SysMenuVO> getMenuVOList(Integer roleId) {
        List<SysMenu> menuList = sysMenuMapper.selectListByRoleId(roleId);
        return menuList.stream().map(m -> sysConvert.menu2MenuVO(m)).toList();
    }

    @Override
    public SysMenuVO getMenuVO(Integer id) {
        SysMenu menu = sysMenuMapper.selectByPrimaryKey(id);
        return ObjectUtils.isEmpty(menu) || Constants.IS_DELETE.equals(menu.getDeleteStatus()) ?
                new SysMenuVO() : sysConvert.menu2MenuVO(menu);
    }

    @Override
    public int insert(SysMenuForm form) {
        SysMenu menu = sysConvert.form2Menu(form);
        return sysMenuMapper.insertSelective(menu);
    }

    @Override
    public int update(Integer id, SysMenuForm form) {
        SysMenu menu = sysConvert.form2Menu(form);
        menu.setId(id);
        return sysMenuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public int delete(Integer id) {
        SysMenu menu = new SysMenu();
        menu.setId(id);
        menu.setDeleteStatus(Constants.IS_DELETE);
        return sysMenuMapper.updateByPrimaryKeySelective(menu);
    }

}
