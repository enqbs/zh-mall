package com.enqbs.app.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChangeNicknameForm {

    @NotBlank(message = "昵称不能为空")
    private String nickName;

}
