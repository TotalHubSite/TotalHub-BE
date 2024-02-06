package com.totalhubsite.backend.global.security.dto;

import com.totalhubsite.backend.domain.member.entity.Member;
import java.util.Arrays;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class PrincipalDetails implements UserDetails {

    private Member member;

    public PrincipalDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(member.getRole().name()));
    }

    // 아이디 반환
    @Override
    public String getUsername() {
        return member.getEmail();
    }

    // 비밀번호 반환
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // 계정 만료여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 인증 만료여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}
