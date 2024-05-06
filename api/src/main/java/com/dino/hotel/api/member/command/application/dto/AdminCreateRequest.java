package com.dino.hotel.api.member.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateRequest {
    MemberCreateRequest memberCreateRequest;

    public static AdminCreateRequest of(MemberCreateRequest memberCreateRequest){
        return new AdminCreateRequest(memberCreateRequest);
    }
}
