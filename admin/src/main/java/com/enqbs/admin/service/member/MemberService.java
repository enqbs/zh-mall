package com.enqbs.admin.service.member;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.vo.MemberVO;
import com.enqbs.common.util.PageUtil;

public interface MemberService {

    /*
     * 会员列表
     * */
    PageUtil<MemberVO> getMemberVOList(Integer id, Long uid, String identifier,
                                       Integer status, Integer deleteStatus, SortEnum sort,
                                       Integer pageNum, Integer pageSize);

    /*
     * 会员信息
     * */
    MemberVO getMemberVO(Integer id);

}
