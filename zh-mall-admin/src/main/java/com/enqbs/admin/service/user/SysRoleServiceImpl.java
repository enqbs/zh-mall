package com.enqbs.admin.service.user;

import com.enqbs.admin.vo.SysRoleVO;
import com.enqbs.generator.dao.SysRoleMapper;
import com.enqbs.generator.pojo.SysRole;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRoleVO> getSysRoleVOList() {
        List<SysRole> sysRoleList = sysRoleMapper.selectListByAll();
        return sysRoleList.stream().map(this::sysRole2SysRoleVO).collect(Collectors.toList());
    }

    private SysRoleVO sysRole2SysRoleVO(SysRole sysRole) {
        SysRoleVO sysRoleVO = new SysRoleVO();
        BeanUtils.copyProperties(sysRole, sysRoleVO);
        return sysRoleVO;
    }

}
