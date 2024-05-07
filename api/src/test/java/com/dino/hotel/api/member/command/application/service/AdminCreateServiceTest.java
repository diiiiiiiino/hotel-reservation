package com.dino.hotel.api.member.command.application.service;

import com.dino.hotel.api.common.exception.ValidationCode;
import com.dino.hotel.api.common.exception.ValidationError;
import com.dino.hotel.api.member.command.application.dto.AdminCreateRequest;
import com.dino.hotel.api.member.command.application.validator.AdminCreateRequestValidator;
import com.dino.hotel.api.member.command.domain.Member;
import com.dino.hotel.api.member.command.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class AdminCreateServiceTest {

    private MemberRepository memberRepository;
    private AdminCreateService adminCreateService;
    private AdminCreateRequestValidator adminCreateRequestValidator;

    public AdminCreateServiceTest() {
        memberRepository = mock(MemberRepository.class);
        adminCreateRequestValidator = new AdminCreateRequestValidator();
        adminCreateService = new AdminCreateService(memberRepository, new BCryptPasswordEncoder(), adminCreateRequestValidator);
    }

    @DisplayName("관리자 정보가 없는 경우")
    @Test
    void createAdminWithMissMemberCreateRequest() {
        AdminCreateRequest adminCreateRequest = AdminCreateRequest.of(null, "qwer1234!@", "홍길동", "01012345678");

        assertThatThrownBy(() -> adminCreateService.create(adminCreateRequest))
                .hasMessage("Request has invalid values")
                .hasFieldOrPropertyWithValue("errors", List.of(
                        ValidationError.of("memberLoginId", ValidationCode.NO_TEXT.getValue())
                ));
    }

    @DisplayName("관리자 생성")
    @Test
    void createAdminSuccess() {
        AdminCreateRequest adminCreateRequest = AdminCreateRequest.of("loginId", "qwer1234!@", "홍길동", "01012345678");

        adminCreateService.create(adminCreateRequest);

        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        BDDMockito.then(memberRepository).should().save(memberCaptor.capture());

        Member savedMember = memberCaptor.getValue();

        assertThat(savedMember.getLoginId()).isEqualTo("loginId");
        assertThat(savedMember.getPassword().match("qwer1234!@", new BCryptPasswordEncoder())).isTrue();
        assertThat(savedMember.getMobile().toString()).isEqualTo("01012345678");
        assertThat(savedMember.getName()).isEqualTo("홍길동");
    }
}
