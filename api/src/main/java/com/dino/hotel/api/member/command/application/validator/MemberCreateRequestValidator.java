package com.dino.hotel.api.member.command.application.validator;

import com.dino.hotel.api.common.exception.ValidationCode;
import com.dino.hotel.api.common.exception.ValidationError;
import com.dino.hotel.api.common.validator.RequestValidator;
import com.dino.hotel.api.common.validator.Validator;
import com.dino.hotel.api.member.command.application.dto.MemberCreateRequest;
import com.dino.hotel.api.member.command.application.validator.annotation.MemberCreateRequestQualifier;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.dino.hotel.api.common.enumeration.TextLengthRange.*;

/**
 * {@code MemberCreateRequest}의 변수의 유효성을 검증하는 클래스
 */
@Validator
@MemberCreateRequestQualifier
public class MemberCreateRequestValidator implements RequestValidator<MemberCreateRequest> {

    /**
     * {@code MemberCreateRequest} 유효성 검증
     * @param request 회원 생성 요청
     * @return List<ValidationError>
     */
    public List<ValidationError> validate(MemberCreateRequest request){
        List<ValidationError> errors = new ArrayList<>();

        if(request == null){
            errors.add(ValidationError.of("request", ValidationCode.NULL.getValue()));
        } else {
            String loginId = request.getLoginId();
            String password = request.getPassword();
            String name = request.getName();
            String mobile = request.getMobile();

            if(!StringUtils.hasText(loginId)){
                errors.add(ValidationError.of("memberLoginId", ValidationCode.NO_TEXT.getValue()));
            } else {
                int length = loginId.length();
                if(LOGIN_ID.getMin() > length || LOGIN_ID.getMax() < length)
                    errors.add(ValidationError.of("memberLoginId", ValidationCode.LENGTH.getValue()));
            }

            if(!StringUtils.hasText(password)){
                errors.add(ValidationError.of("memberPassword", ValidationCode.NO_TEXT.getValue()));
            } else {
                int length = password.length();
                if(PASSWORD.getMin() > length || PASSWORD.getMax() < length)
                    errors.add(ValidationError.of("memberPassword", ValidationCode.LENGTH.getValue()));
            }

            if(!StringUtils.hasText(name)){
                errors.add(ValidationError.of("memberName", ValidationCode.NO_TEXT.getValue()));
            } else {
                int length = name.length();
                if(MEMBER_NAME.getMin() > length || MEMBER_NAME.getMax() < length)
                    errors.add(ValidationError.of("memberName", ValidationCode.LENGTH.getValue()));
            }

            if(!StringUtils.hasText(mobile)){
                errors.add(ValidationError.of("memberMobile", ValidationCode.NO_TEXT.getValue()));
            } else {
                int length = mobile.length();
                if(MOBILE.getMin() > length || MOBILE.getMax() < mobile.length())
                    errors.add(ValidationError.of("memberMobile", ValidationCode.LENGTH.getValue()));
            }
        }

        return errors;
    }
}
