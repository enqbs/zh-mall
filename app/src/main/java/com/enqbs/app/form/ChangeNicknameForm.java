package com.enqbs.app.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeNicknameForm {

    @NotBlank(message = "昵称不能为空")
    private String nickName;

}
