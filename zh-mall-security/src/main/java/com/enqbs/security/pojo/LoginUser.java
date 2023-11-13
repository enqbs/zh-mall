package com.enqbs.security.pojo;

import com.google.gson.annotations.Expose;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LoginUser implements UserDetails {

    @Expose(serialize = false, deserialize = false)
    private String userToken;

    @Expose(serialize = false, deserialize = false)
    private String userType;

    private Integer userId;

    private String username;

    private String password;

    private Long uid;

    private String nickName;

    private String photo;

    private Integer gender;

    private Integer experience;

    private Integer level;

    private String levelTitle;

    private Integer levelExperience;

    private BigDecimal discount;

    private List<String> permissionList;

    @Expose(serialize = false, deserialize = false)
    private List<SimpleGrantedAuthority> simpleGrantedAuthorityList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (!CollectionUtils.isEmpty(permissionList)) {
            simpleGrantedAuthorityList = permissionList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        return simpleGrantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelTitle() {
        return levelTitle;
    }

    public void setLevelTitle(String title) {
        this.levelTitle = title;
    }

    public Integer getLevelExperience() {
        return levelExperience;
    }

    public void setLevelExperience(Integer levelExperience) {
        this.levelExperience = levelExperience;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

    public List<SimpleGrantedAuthority> getSimpleGrantedAuthorityList() {
        return simpleGrantedAuthorityList;
    }

    public void setSimpleGrantedAuthorityList(List<SimpleGrantedAuthority> simpleGrantedAuthorityList) {
        this.simpleGrantedAuthorityList = simpleGrantedAuthorityList;
    }

}
