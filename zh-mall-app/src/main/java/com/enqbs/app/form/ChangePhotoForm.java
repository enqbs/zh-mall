package com.enqbs.app.form;

import javax.validation.constraints.NotBlank;

public class ChangePhotoForm {

    @NotBlank(message = "用户头像地址不能为空")
    private String photoURL;

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String userPhotoURL) {
        this.photoURL = photoURL;
    }

}
