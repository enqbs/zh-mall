package com.enqbs.security.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Setter
public class LoginUser implements UserDetails {

    @Getter
    private String userToken;

    @Getter
    private transient String userType;

    @Getter
    private Integer userId;

    private String username;

    private String password;

    @Getter
    private Long uid;

    @Getter
    private String nickName;

    @Getter
    private String photo;

    @Getter
    private Integer gender;

    @Getter
    private Integer experience;

    @Getter
    private Integer level;

    @Getter
    private String levelTitle;

    @Getter
    private Integer levelExperience;

    @Getter
    private BigDecimal discount;

    private List<String> permissionList;

    @Getter
    private transient List<SimpleGrantedAuthority> simpleGrantedAuthorityList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return CollectionUtils.isEmpty(permissionList) ? simpleGrantedAuthorityList : permissionList.stream().map(SimpleGrantedAuthority::new).toList();
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

}
