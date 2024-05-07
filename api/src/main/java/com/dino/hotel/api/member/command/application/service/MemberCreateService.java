package com.dino.hotel.api.member.command.application.service;

import com.dino.hotel.api.common.component.DateUtils;
import com.dino.hotel.api.common.exception.ValidationError;
import com.dino.hotel.api.common.exception.ValidationErrorException;
import com.dino.hotel.api.common.validator.RequestValidator;
import com.dino.hotel.api.member.command.application.dto.MemberCreateRequest;
import com.dino.hotel.api.member.command.application.validator.annotation.MemberCreateRequestQualifier;
import com.dino.hotel.api.member.command.domain.Member;
import com.dino.hotel.api.member.command.domain.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 회원 생성 서비스
 */
@Service
public class MemberCreateService {
    private final DateUtils dateUtils;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RequestValidator validator;

    public MemberCreateService(DateUtils dateUtils,
                               PasswordEncoder passwordEncoder,
                               MemberRepository memberRepository,
                               @MemberCreateRequestQualifier RequestValidator validator) {
        this.dateUtils = dateUtils;
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        this.validator = validator;
    }

    /**
     * 회원 생성
     * @param request 회원 생성 요청
     * @throws ValidationErrorException
     * <ul>
     *     <li>{@code loginId}가 {@code null}이거나 문자가 없을 경우, 길이가 1~15 아닌 경우
     *     <li>{@code password}가 {@code null}이거나 문자가 없을 경우, 길이가 8~16 아닌 경우
     *     <li>{@code name}이 {@code null}이거나 문자가 없을 경우, 길이가 1~15 아닌 경우
     *     <li>{@code mobile}이 {@code null}이거나 문자가 없을 경우, 길이가 11자리가 아닌 경우
     * </ul>
     */
    @Transactional
    public void create(MemberCreateRequest request) {
        List<ValidationError> errors = validator.validate(request);
        if(!errors.isEmpty())
            throw new ValidationErrorException("Request has invalid values", errors);

        Member member = MemberCreateRequest.newMember(request, passwordEncoder);
        memberRepository.save(member);
    }
}
