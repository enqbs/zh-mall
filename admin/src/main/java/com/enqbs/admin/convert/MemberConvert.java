package com.enqbs.admin.convert;

import com.enqbs.admin.vo.MemberLevelVO;
import com.enqbs.admin.vo.MemberVO;
import com.enqbs.generator.pojo.User;
import com.enqbs.generator.pojo.UserLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberConvert {

    @Mapping(target = "levelInfo", ignore = true)
    MemberVO user2MemberVO(User user);

    MemberLevelVO userLevel2MemberLevelVO(UserLevel userLevel);

}
