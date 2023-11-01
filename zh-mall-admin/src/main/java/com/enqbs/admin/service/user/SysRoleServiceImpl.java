package com.enqbs.admin.service.user;

import com.enqbs.admin.form.SysRoleForm;
import com.enqbs.admin.vo.SysRoleVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.SysRoleMapper;
import com.enqbs.generator.pojo.SysRole;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageUtil<SysRoleVO> getSysRoleVOList(Integer deleteStatus, Integer pageNum, Integer pageSize) {
        PageUtil<SysRoleVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<SysRoleVO> sysRoleVOList = new ArrayList<>();
        List<SysRole> sysRoleList = sysRoleMapper.selectListByParam(deleteStatus, pageNum, pageSize);

        if (CollectionUtils.isEmpty(sysRoleList)) {
            pageUtil.setTotal(total);
            pageUtil.setList(sysRoleVOList);
            return pageUtil;
        }

        total = sysRoleMapper.countByParam(deleteStatus);
        sysRoleVOList = sysRoleList.stream().map(this::sysRole2SysRoleVO).collect(Collectors.toList());
        pageUtil.setTotal(total);
        pageUtil.setList(sysRoleVOList);
        return pageUtil;
    }

    @Override
    public SysRoleVO getSysRoleVO(Integer id) {
        SysRoleVO sysRoleVO = new SysRoleVO();
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);

        if (ObjectUtils.isEmpty(sysRole) || Constants.IS_DELETE.equals(sysRole.getDeleteStatus())) {
            return sysRoleVO;
        }

        sysRoleVO = sysRole2SysRoleVO(sysRole);
        return sysRoleVO;
    }

    @Override
    public int insetSysRole(SysRoleForm form) {
        SysRole sysRole = sysRoleForm2SysRole(form);
        return sysRoleMapper.insertSelective(sysRole);
    }

    @Override
    public int updateSysRole(Integer id, SysRoleForm form) {
        SysRole sysRole = sysRoleForm2SysRole(form);
        sysRole.setId(id);
        return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    @Override
    public int deleteSysRole(Integer id) {
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        sysRole.setDeleteStatus(Constants.IS_DELETE);
        return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    private SysRole sysRoleForm2SysRole(SysRoleForm form) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(form, sysRole);
        return sysRole;
    }

    private SysRoleVO sysRole2SysRoleVO(SysRole sysRole) {
        SysRoleVO sysRoleVO = new SysRoleVO();
        BeanUtils.copyProperties(sysRole, sysRoleVO);
        return sysRoleVO;
    }

}
