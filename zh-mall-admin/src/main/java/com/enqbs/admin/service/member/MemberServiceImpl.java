package com.enqbs.admin.service.member;

import com.enqbs.admin.vo.MemberLevelVO;
import com.enqbs.admin.vo.MemberVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.UserLevelMapper;
import com.enqbs.generator.dao.UserMapper;
import com.enqbs.generator.pojo.User;
import com.enqbs.generator.pojo.UserLevel;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserLevelMapper userLevelMapper;

    @Override
    public PageUtil<MemberVO> getMemberVOList(Integer id, Long uid, String identifier,
                                              Integer status, Integer deleteStatus, SortEnum sortEnum,
                                              Integer pageNum, Integer pageSize) {
        PageUtil<MemberVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<MemberVO> memberVOList = new ArrayList<>();
        List<User> userList = userMapper.selectListByParam(id, uid, identifier, status, deleteStatus, sortEnum.getSortType(), pageNum, pageSize);

        if (!CollectionUtils.isEmpty(userList)) {
            total = userMapper.countByParam(id, uid, identifier, status, deleteStatus);
            Set<Integer> levelIdSet = userList.stream().map(User::getLevelId).collect(Collectors.toSet());
            Map<Integer, MemberLevelVO> memberLevelVOMap = userLevelMapper.selectListByIdSet(levelIdSet).stream()
                    .map(this::userLevel2MemberLevelVO).collect(Collectors.toMap(MemberLevelVO::getId, memberLevelVO -> memberLevelVO));
            userList.stream().map(this::user2MemberVO).collect(Collectors.toList()).forEach(memberVO -> {
                memberVO.setLevelInfo(memberLevelVOMap.get(memberVO.getLevelId()));
                memberVOList.add(memberVO);
            });
        }

        pageUtil.setTotal(total);
        pageUtil.setList(memberVOList);
        return pageUtil;
    }

    @Override
    public MemberVO getMemberVO(Integer id) {
        MemberVO memberVO = new MemberVO();
        User user = userMapper.selectByPrimaryKey(id);

        if (ObjectUtils.isEmpty(user) || Constants.IS_DELETE.equals(user.getDeleteStatus())) {
            return memberVO;
        }

        memberVO = user2MemberVO(user);
        UserLevel userLevel = userLevelMapper.selectByPrimaryKey(user.getLevelId());

        if (ObjectUtils.isNotEmpty(userLevel)) {
            MemberLevelVO memberLevelVO = userLevel2MemberLevelVO(userLevel);
            memberVO.setLevelInfo(memberLevelVO);
        }

        return memberVO;
    }

    private MemberVO user2MemberVO(User user) {
        MemberVO memberVO = new MemberVO();
        BeanUtils.copyProperties(user, memberVO);
        return memberVO;
    }

    private MemberLevelVO userLevel2MemberLevelVO(UserLevel userLevel) {
        MemberLevelVO memberLevelVO = new MemberLevelVO();
        BeanUtils.copyProperties(userLevel, memberLevelVO);
        return memberLevelVO;
    }

}
