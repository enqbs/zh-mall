package com.enqbs.admin.service.member;

import com.enqbs.admin.vo.MemberLevelVO;
import com.enqbs.admin.vo.MemberVO;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.UserMapper;
import com.enqbs.generator.pojo.User;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MemberLevelService memberLevelService;

    @Override
    public PageUtil<MemberVO> getMemberVOList(Integer id, Long uid, String identifier,
                                              Integer status, Integer deleteStatus, SortEnum sortEnum,
                                              Integer pageNum, Integer pageSize) {
        PageUtil<MemberVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<User> userList = userMapper.selectListByParam(id, uid, identifier, status, deleteStatus, sortEnum.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(userList)) {
            pageUtil.setTotal(total);
            pageUtil.setList(Collections.emptyList());
            return pageUtil;
        }

        total = userMapper.countByParam(id, uid, identifier, status, deleteStatus);
        Set<Integer> levelIdSet = userList.stream().map(User::getLevelId).collect(Collectors.toSet());
        Map<Integer, MemberLevelVO> memberLevelVOMap = memberLevelService
                .getMemberLevelVOList(levelIdSet).stream().collect(Collectors.toMap(MemberLevelVO::getId, v -> v));
        List<MemberVO> memberVOList = userList.stream().map(e -> {
            MemberVO memberVO = user2MemberVO(e);
            memberVO.setLevelInfo(memberLevelVOMap.get(memberVO.getLevelId()));
            return memberVO;
        }).collect(Collectors.toList());
        pageUtil.setTotal(total);
        pageUtil.setList(memberVOList);
        return pageUtil;
    }

    @Override
    public MemberVO getMemberVO(Integer id) {
        MemberVO memberVO = new MemberVO();
        User user = userMapper.selectByPrimaryKey(id);

        if (ObjectUtils.isEmpty(user)) {
            return memberVO;
        }

        memberVO = user2MemberVO(user);
        MemberLevelVO memberLevelVO = memberLevelService.getMemberLevelVO(user.getLevelId());

        if (ObjectUtils.isNotEmpty(memberLevelVO)) {
            memberVO.setLevelInfo(memberLevelVO);
        }

        return memberVO;
    }

    private MemberVO user2MemberVO(User user) {
        MemberVO memberVO = new MemberVO();
        BeanUtils.copyProperties(user, memberVO);
        return memberVO;
    }

}
