package com.enqbs.admin.service.member;

import com.enqbs.admin.convert.MemberConvert;
import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.vo.MemberLevelVO;
import com.enqbs.admin.vo.MemberVO;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.UserMapper;
import com.enqbs.generator.pojo.User;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Resource
    private MemberConvert memberConvert;

    @Override
    public PageUtil<MemberVO> memberVOPage(Integer id, Long uid, String identifier, Integer status,
                                           Integer deleteStatus, SortEnum sort, Integer pageNum, Integer pageSize) {
        PageUtil<MemberVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        List<User> userList = userMapper.selectListByParam(id, uid, identifier, status, deleteStatus, sort.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(userList)) {
            return pageUtil;
        }

        Long total = userMapper.countByParam(id, uid, identifier, status, deleteStatus);
        Set<Integer> levelIdSet = userList.stream().map(User::getLevelId).collect(Collectors.toSet());
        Map<Integer, MemberLevelVO> memberLevelVOMap = memberLevelService.getMemberLevelVOList(levelIdSet).stream()
                .collect(Collectors.toMap(MemberLevelVO::getId, v -> v));
        List<MemberVO> memberVOList = userList.stream().map(u -> {
                    MemberVO memberVO = memberConvert.user2MemberVO(u);
                    memberVO.setLevelInfo(memberLevelVOMap.get(memberVO.getLevelId()));
                    return memberVO;
                }
        ).toList();
        pageUtil.setTotal(total);
        pageUtil.setList(memberVOList);
        return pageUtil;
    }

    @Override
    public MemberVO getMemberVO(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);

        if (ObjectUtils.isEmpty(user)) {
            return new MemberVO();
        }

        MemberVO memberVO = memberConvert.user2MemberVO(user);
        MemberLevelVO memberLevelVO = memberLevelService.getMemberLevelVO(user.getLevelId());

        if (ObjectUtils.isNotEmpty(memberLevelVO)) {
            memberVO.setLevelInfo(memberLevelVO);
        }

        return memberVO;
    }

}
