package com.enqbs.admin.controller;

import com.enqbs.admin.service.member.MemberService;
import com.enqbs.admin.vo.MemberVO;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/member/list")
    public R<PageUtil<MemberVO>> memberList(@RequestParam(required = false) Integer id,
                                            @RequestParam(required = false) Long uid,
                                            @RequestParam(required = false) String identifier,
                                            @RequestParam(required = false) Integer status,
                                            @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                            @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize <= 0) {
            pageSize = 10;
        }

        PageUtil<MemberVO> pageMemberVOList = memberService.getMemberVOList(id, uid, identifier, status, deleteStatus, sort, pageNum, pageSize);
        return R.ok(pageMemberVOList);
    }

    @GetMapping("/member/{id}")
    public R<MemberVO> memberDetail(@PathVariable Integer id) {
        MemberVO memberVO = memberService.getMemberVO(id);
        return R.ok(memberVO);
    }

}
