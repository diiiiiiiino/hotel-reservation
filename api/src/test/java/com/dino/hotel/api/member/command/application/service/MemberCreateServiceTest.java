package com.dino.hotel.api.member.command.application.service;

import com.dino.hotel.api.authority.command.domain.enumeration.AuthorityEnum;
import com.dino.hotel.api.common.component.DateUtils;
import com.dino.hotel.api.common.exception.ValidationCode;
import com.dino.hotel.api.common.exception.ValidationError;
import com.dino.hotel.api.common.exception.ValidationErrorException;
import com.dino.hotel.api.member.command.application.dto.MemberCreateRequest;
import com.dino.hotel.api.member.command.application.validator.MemberCreateRequestValidator;
import com.dino.hotel.api.member.command.domain.Member;
import com.dino.hotel.api.member.command.domain.MemberAuthority;
import com.dino.hotel.api.member.command.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MemberCreateServiceTest {

    private DateUtils dateUtils;
    private MemberRepository memberRepository;
    private MemberCreateRequestValidator validator;
    private MemberCreateService memberCreateService;

    public MemberCreateServiceTest() {
        dateUtils = mock(DateUtils.class);
        memberRepository = mock(MemberRepository.class);
        validator = new MemberCreateRequestValidator();
        memberCreateService = new MemberCreateService(dateUtils, new BCryptPasswordEncoder(), memberRepository, validator);
    }

    @DisplayName("생성 요청 파라미터 유효성 오류")
    @Test
    void requestValueInvalid() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("", "qwer12!", "홍길동", null);

        assertThatThrownBy(() -> memberCreateService.create(memberCreateRequest))
                .isInstanceOf(ValidationErrorException.class)
                .hasMessage("Request has invalid values")
                .hasFieldOrPropertyWithValue("errors", List.of(
                        ValidationError.of("memberLoginId", ValidationCode.NO_TEXT.getValue()),
                        ValidationError.of("memberPassword", ValidationCode.LENGTH.getValue()),
                        ValidationError.of("memberMobile", ValidationCode.NO_TEXT.getValue())
                ));
    }

    @DisplayName("회원 생성")
    @Test
    void createMemberSuccess() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("loginId", "qwer1234!@", "홍길동", "01012345678");

        when(dateUtils.today()).thenReturn(LocalDateTime.of(2023, 8, 7, 20, 15, 0));

        memberCreateService.create(memberCreateRequest);

        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        BDDMockito.then(memberRepository).should().save(memberCaptor.capture());

        Member savedMember = memberCaptor.getValue();
        assertThat(savedMember.getLoginId()).isEqualTo("loginId");
        assertThat(savedMember.getPassword().match("qwer1234!@", new BCryptPasswordEncoder())).isTrue();
        assertThat(savedMember.getMobile().toString()).isEqualTo("01012345678");
        assertThat(savedMember.getName()).isEqualTo("홍길동");

        Set<String> authoritySet = savedMember.getAuthorities()
                .stream()
                .map(MemberAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertThat(authoritySet).hasSize(1);
        assertThat(authoritySet).containsOnly(AuthorityEnum.ROLE_MEMBER.getName());
    }
}
