package com.enqbs.admin.service.member;

import com.enqbs.admin.vo.MemberLevelVO;
import com.enqbs.generator.dao.UserLevelMapper;
import com.enqbs.generator.pojo.UserLevel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MemberLevelServiceImpl implements MemberLevelService {

    @Resource
    private UserLevelMapper userLevelMapper;

    @Override
    public List<MemberLevelVO> getMemberLevelVOList(Set<Integer> userLevelIdSet) {
        List<UserLevel> userLevelList = userLevelMapper.selectListByIdSet(userLevelIdSet);
        return userLevelList.stream().map(this::userLevel2MemberLevelVO).collect(Collectors.toList());
    }

    @Override
    public MemberLevelVO getMemberLevelVO(Integer userLevelId) {
        UserLevel userLevel = userLevelMapper.selectByPrimaryKey(userLevelId);
        return userLevel2MemberLevelVO(userLevel);
    }

    private MemberLevelVO userLevel2MemberLevelVO(UserLevel userLevel) {
        MemberLevelVO memberLevelVO = new MemberLevelVO();
        BeanUtils.copyProperties(userLevel, memberLevelVO);
        return memberLevelVO;
    }

}
