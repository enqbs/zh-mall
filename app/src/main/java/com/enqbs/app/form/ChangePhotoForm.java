package com.enqbs.app.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePhotoForm {

    @NotBlank(message = "用户头像地址不能为空")
    private String photoURL;

}
