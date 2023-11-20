package com.enqbs.admin.service.member;

import com.enqbs.admin.vo.MemberLevelVO;

import java.util.List;
import java.util.Set;

public interface MemberLevelService {

    List<MemberLevelVO> getMemberLevelVOList(Set<Integer> userLevelIdSet);

    MemberLevelVO getMemberLevelVO(Integer userLevelId);

}
