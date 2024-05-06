package com.dino.hotel.api.member.query;

import com.dino.hotel.api.member.command.domain.Mobile;
import lombok.Getter;

@Getter
public class MemberDto {
    String loginId;
    String name;
    String mobile;


    public MemberDto(String loginId,
                     String name,
                     Mobile mobile) {
        this.loginId = loginId;
        this.name = name;
        this.mobile = mobile.getValue();
    }
}