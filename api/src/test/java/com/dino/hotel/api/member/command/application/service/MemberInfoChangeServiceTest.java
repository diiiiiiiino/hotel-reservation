package com.dino.hotel.api.member.command.application.service;

import com.dino.hotel.api.common.exception.ValidationCode;
import com.dino.hotel.api.common.exception.ValidationError;
import com.dino.hotel.api.common.exception.ValidationErrorException;
import com.dino.hotel.api.helper.builder.MemberCreateHelperBuilder;
import com.dino.hotel.api.member.command.application.dto.MemberInfoChangeRequest;
import com.dino.hotel.api.member.command.application.exception.MemberNotFoundException;
import com.dino.hotel.api.member.command.application.validator.MemberInfoChangeRequestValidator;
import com.dino.hotel.api.member.command.domain.Member;
import com.dino.hotel.api.member.command.domain.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MemberInfoChangeServiceTest {

    private MemberRepository memberRepository;
    private MemberInfoChangeRequestValidator validator;
    private MemberInfoChangeService memberInfoChangeService;

    public MemberInfoChangeServiceTest() {
        memberRepository = mock(MemberRepository.class);
        validator = new MemberInfoChangeRequestValidator();
        memberInfoChangeService = new MemberInfoChangeService(memberRepository, validator);
    }

    @DisplayName("변경 요청 파라미터 유효성 오류")
    @Test
    void requestValueInvalid() {
        MemberInfoChangeRequest request = MemberInfoChangeRequest.builder()
                .name(null)
                .mobile("010125678")
                .build();

        Member member = MemberCreateHelperBuilder.builder().id(1L).build();

        Assertions.assertThatThrownBy(() -> memberInfoChangeService.change(member.getId(), request))
                .isInstanceOf(ValidationErrorException.class)
                .hasMessage("Request has invalid values")
                .hasFieldOrPropertyWithValue("errors", List.of(
                        ValidationError.of("memberName", ValidationCode.NO_TEXT.getValue()),
                        ValidationError.of("memberMobile", ValidationCode.LENGTH.getValue())
                ));
    }

    @DisplayName("회원 정보가 존재하지 않을 경우")
    @Test
    void memberNotFound() {
        MemberInfoChangeRequest request = MemberInfoChangeRequest.builder()
                .name("홍길동")
                .mobile("01012345678")
                .build();

        Member member = MemberCreateHelperBuilder.builder().id(1L).build();

        Assertions.assertThatThrownBy(() -> memberInfoChangeService.change(member.getId(), request))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("Member not found");
    }

    @DisplayName("회원정보 변경")
    @Test
    void changeMemberInfo() {
        MemberInfoChangeRequest request = MemberInfoChangeRequest.builder()
                .name("홍길동")
                .mobile("01012345678")
                .build();

        Member member = MemberCreateHelperBuilder.builder().id(1L).build();

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        memberInfoChangeService.change(member.getId(), request);

        assertThat(member.getName()).isEqualTo("홍길동");
        assertThat(member.getMobile().toString()).isEqualTo("01012345678");
    }
}
