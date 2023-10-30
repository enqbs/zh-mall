package com.enqbs.admin.service.user;

import com.enqbs.admin.vo.SysMenuVO;
import com.enqbs.generator.dao.SysMenuMapper;
import com.enqbs.generator.pojo.SysMenu;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public Set<SysMenu> getSysMenuSet(String username) {
        return sysMenuMapper.selectSetByUsername(username);
    }

    private SysMenuVO sysMenu2SysMenuVO(SysMenu sysMenu) {
        SysMenuVO sysMenuVO = new SysMenuVO();
        BeanUtils.copyProperties(sysMenu, sysMenuVO);
        return sysMenuVO;
    }

}
