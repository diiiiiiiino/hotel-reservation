package com.dino.hotel.api.member.command.application.dto;

import com.dino.hotel.api.authority.command.domain.Authority;
import com.dino.hotel.api.authority.command.domain.enumeration.AuthorityEnum;
import com.dino.hotel.api.member.command.domain.Member;
import com.dino.hotel.api.member.command.domain.MemberAuthority;
import com.dino.hotel.api.member.command.domain.Mobile;
import com.dino.hotel.api.member.command.domain.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.function.Function;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateRequest {
    private String loginId;
    private String password;
    private String name;
    private String mobile;

    public static MemberCreateRequest of(String loginId, String password, String name, String mobile){
        return new MemberCreateRequest(loginId, password, name, mobile);
    }

    public static Member newMember(MemberCreateRequest request, PasswordEncoder passwordEncoder){
        List<Function<Member, MemberAuthority>> functions = List.of(member -> MemberAuthority.of(member, Authority.of(AuthorityEnum.ROLE_MEMBER)));
        return Member.of(request.getLoginId(), Password.of(request.getPassword(), passwordEncoder), request.getName(), Mobile.of(request.getMobile()), functions);
    }

    public static Member newAdmin(MemberCreateRequest request, PasswordEncoder passwordEncoder){
        List<Function<Member, MemberAuthority>> functions = List.of(
                member -> MemberAuthority.of(member, Authority.of(AuthorityEnum.ROLE_ADMIN)),
                member -> MemberAuthority.of(member, Authority.of(AuthorityEnum.ROLE_MEMBER)));
        return Member.of(request.getLoginId(), Password.of(request.getPassword(), passwordEncoder), request.getName(), Mobile.of(request.getMobile()), functions);
    }
}
