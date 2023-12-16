package com.enqbs.app.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChangePhotoForm {

    @NotBlank(message = "用户头像地址不能为空")
    private String photoURL;

}
