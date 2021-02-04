package com.brframework.commonsecurity.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author xu
 * @Date 2018/1/31 0031 下午 7:09
 * 用户对象
 */
public class SecurityUserDetails implements UserDetails {

    private String id;
    private String username;
    private String password;
    private Map<String, String> expand;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private List<GrantedAuthority> authorities = Lists.newArrayList();

    /**
     * @param id       用户ID
     * @param username 用户登录名
     * @param password 用户密码
     * @param auths    权限验证
     * @param enabled 用户没有被禁用
     */
    public SecurityUserDetails(String id, String username, String password, List<String> auths,
                               boolean enabled, Map<String, String> expand) {

        this.id = id;
        this.username = username;
        this.password = password;
        auths.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        this.enabled = enabled;
        this.expand = expand;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
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
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getExpand(){
        return expand;
    }
}
