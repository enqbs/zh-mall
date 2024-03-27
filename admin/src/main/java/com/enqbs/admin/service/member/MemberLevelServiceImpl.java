package com.enqbs.admin.service.member;

import com.enqbs.admin.convert.MemberConvert;
import com.enqbs.admin.vo.MemberLevelVO;
import com.enqbs.generator.dao.UserLevelMapper;
import com.enqbs.generator.pojo.UserLevel;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MemberLevelServiceImpl implements MemberLevelService {

    @Resource
    private UserLevelMapper userLevelMapper;

    @Resource
    private MemberConvert memberConvert;

    @Override
    public List<MemberLevelVO> getMemberLevelVOList(Set<Integer> userLevelIdSet) {
        if (CollectionUtils.isEmpty(userLevelIdSet)) {
            return Collections.emptyList();
        }

        List<UserLevel> userLevelList = userLevelMapper.selectListByIdSet(userLevelIdSet);
        return CollectionUtils.isEmpty(userLevelList) ? Collections.emptyList() : userLevelList.stream().map(u -> memberConvert.userLevel2MemberLevelVO(u)).collect(Collectors.toList());
    }

    @Override
    public MemberLevelVO getMemberLevelVO(Integer userLevelId) {
        UserLevel userLevel = userLevelMapper.selectByPrimaryKey(userLevelId);
        return ObjectUtils.isEmpty(userLevel) ? null : memberConvert.userLevel2MemberLevelVO(userLevel);
    }

}
