package com.example.itjobportal.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // Đọc trường "roles" (dạng mảng) mà ta đã ghi vào JWT ở bước trước
        List<String> roles = jwt.getClaimAsStringList("roles");

        if (roles == null) {
            roles = List.of();
        }

        // Ép kiểu chuỗi string thành Authority trong hệ thống Spring Security
        Collection<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new JwtAuthenticationToken(jwt, authorities);
    }
}
