package com.enqbs.admin.service.user;

import com.enqbs.admin.convert.SysUserConvert;
import com.enqbs.admin.form.SysRoleForm;
import com.enqbs.admin.vo.SysRoleVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.SysRoleMapper;
import com.enqbs.generator.pojo.SysRole;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserConvert sysUserConvert;

    @Override
    public PageUtil<SysRoleVO> getSysRoleVOList(Integer deleteStatus, Integer pageNum, Integer pageSize) {
        PageUtil<SysRoleVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        List<SysRole> sysRoleList = sysRoleMapper.selectListByParam(deleteStatus, pageNum, pageSize);

        if (CollectionUtils.isEmpty(sysRoleList)) {
            return pageUtil;
        }

        Long total = sysRoleMapper.countByParam(deleteStatus);
        pageUtil.setTotal(total);
        pageUtil.setList(sysRoleList.stream().map(e -> sysUserConvert.sysRole2SysRoleVO(e)).collect(Collectors.toList()));
        return pageUtil;
    }

    @Override
    public SysRoleVO getSysRoleVO(Integer id) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        return ObjectUtils.isEmpty(sysRole) || Constants.IS_DELETE.equals(sysRole.getDeleteStatus()) ?
                new SysRoleVO() : sysUserConvert.sysRole2SysRoleVO(sysRole);
    }

    @Override
    public int insert(SysRoleForm form) {
        SysRole sysRole = sysUserConvert.sysRoleForm2SysRole(form);
        return sysRoleMapper.insertSelective(sysRole);
    }

    @Override
    public int update(Integer id, SysRoleForm form) {
        SysRole sysRole = sysUserConvert.sysRoleForm2SysRole(form);
        sysRole.setId(id);
        return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    @Override
    public int delete(Integer id) {
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        sysRole.setDeleteStatus(Constants.IS_DELETE);
        return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

}
