package com.dino.hotel.api.security;

import com.dino.hotel.api.helper.builder.MemberCreateHelperBuilder;
import com.dino.hotel.api.member.command.application.security.SecurityMember;
import com.dino.hotel.api.member.command.domain.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithMockMemberSecurityContextFactory implements WithSecurityContextFactory<WithMockMember> {

    @Override
    public SecurityContext createSecurityContext(WithMockMember annotation) {
        Member member = MemberCreateHelperBuilder.builder().build();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(SecurityMember.from(member), "pw", List.of(new SimpleGrantedAuthority("MEMBER")));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);

        return context;
    }
}
