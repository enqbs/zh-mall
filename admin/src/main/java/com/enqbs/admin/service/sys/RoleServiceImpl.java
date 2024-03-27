package com.enqbs.admin.service.sys;

import com.enqbs.admin.convert.SysConvert;
import com.enqbs.admin.form.SysRoleForm;
import com.enqbs.admin.vo.SysRoleVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.SysRoleMapper;
import com.enqbs.generator.pojo.SysRole;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysConvert sysConvert;

    @Override
    public PageUtil<SysRoleVO> roleVOListPage(Integer deleteStatus, Integer pageNum, Integer pageSize) {
        Long total = sysRoleMapper.countByParam(deleteStatus);
        List<SysRole> roleList = sysRoleMapper.selectListByParam(deleteStatus, pageNum, pageSize);
        PageUtil<SysRoleVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        pageUtil.setTotal(total);
        pageUtil.setList(roleList.stream().map(r -> sysConvert.role2RoleVO(r)).toList());
        return pageUtil;
    }

    @Override
    public SysRoleVO getRoleVO(Integer id) {
        SysRole role = sysRoleMapper.selectByPrimaryKey(id);
        return ObjectUtils.isEmpty(role) || Constants.IS_DELETE.equals(role.getDeleteStatus()) ? null : sysConvert.role2RoleVO(role);
    }

    @Override
    public int insert(SysRoleForm form) {
        SysRole role = sysConvert.form2Role(form);
        return sysRoleMapper.insertSelective(role);
    }

    @Override
    public int update(Integer id, SysRoleForm form) {
        SysRole role = sysConvert.form2Role(form);
        role.setId(id);
        return sysRoleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int delete(Integer id) {
        SysRole role = new SysRole();
        role.setId(id);
        role.setDeleteStatus(Constants.IS_DELETE);
        return sysRoleMapper.updateByPrimaryKeySelective(role);
    }

}
