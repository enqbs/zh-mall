package com.enqbs.admin.controller;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.service.member.MemberService;
import com.enqbs.admin.vo.MemberVO;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/list")
    public R<PageUtil<MemberVO>> memberPage(@RequestParam(required = false) Integer id,
                                            @RequestParam(required = false) Long uid,
                                            @RequestParam(required = false) String identifier,
                                            @RequestParam(required = false) Integer status,
                                            @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                            @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<MemberVO> memberVOListPage = memberService.memberVOListPage(id, uid, identifier, status,
                deleteStatus, sort, pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(memberVOListPage);
    }

    @GetMapping("/{id}")
    public R<MemberVO> memberDetail(@PathVariable Integer id) {
        MemberVO memberInfo = memberService.getMemberVO(id);
        return R.ok(memberInfo);
    }

}
