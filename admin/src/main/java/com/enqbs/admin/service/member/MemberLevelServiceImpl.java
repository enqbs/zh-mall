package com.enqbs.admin.service.member;

import com.enqbs.admin.convert.MemberConvert;
import com.enqbs.admin.vo.MemberLevelVO;
import com.enqbs.generator.dao.UserLevelMapper;
import com.enqbs.generator.pojo.UserLevel;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MemberLevelServiceImpl implements MemberLevelService {

    @Resource
    private UserLevelMapper userLevelMapper;

    @Resource
    private MemberConvert memberConvert;

    @Override
    public List<MemberLevelVO> getMemberLevelVOList(Set<Integer> userLevelIdSet) {
        List<UserLevel> userLevelList = userLevelMapper.selectListByIdSet(userLevelIdSet);
        return userLevelList.stream().map(u -> memberConvert.userLevel2MemberLevelVO(u)).toList();
    }

    @Override
    public MemberLevelVO getMemberLevelVO(Integer userLevelId) {
        UserLevel userLevel = userLevelMapper.selectByPrimaryKey(userLevelId);
        return memberConvert.userLevel2MemberLevelVO(userLevel);
    }

}
