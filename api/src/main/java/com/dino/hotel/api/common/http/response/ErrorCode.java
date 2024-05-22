package com.dino.hotel.api.common.http.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Http 응답 에러 코드
 */
@Getter
public enum ErrorCode {

    /**회원 미존재*/
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MemberNotFound"),

    /**호텔 미존재*/
    HOTEL_NOT_FOUND(HttpStatus.NOT_FOUND, "HotelNotFound"),

    /**Room 미존재*/
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "RoomNotFound"),

    /**비밀번호 틀림*/
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "PasswordNotMatched"),

    /**비밀번호 정책 위반*/
    PASSWORD_OUT_OF_CONDITION(HttpStatus.BAD_REQUEST, "PasswordOutOfCondition"),

    /**기존 비밀번호와 변경 비밀번호 동일*/
    UPDATE_PASSWORD_SAME(HttpStatus.BAD_REQUEST, "UpdatePasswordSame"),

    /**유효하지 않은 요청*/
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "InvalidRequest"),

    /**숫자가 음수일때*/
    NEGATIVE_NUMBER(HttpStatus.BAD_REQUEST, "NegativeNumber"),

    /**컬렉션이 {@code null}이거나 비어있을때*/
    ILLEGAL_COLLECTION(HttpStatus.BAD_REQUEST, "IllegalCollection"), 
    
    /**{@code null}이거나 글자가 없을때*/
    NO_TEXT(HttpStatus.BAD_REQUEST, "NoText"),

    /**글자가 지정된 길이가 아닐때*/
    NOT_MATCH_LENGTH(HttpStatus.BAD_REQUEST, "NotMatchLength"),

    /**파라미터가 {@code null}일때*/
    NULL(HttpStatus.BAD_REQUEST, "Null"),

    /**{@code presentMeter}가 {@code previousMeter}보다 작은 경우*/
    PRESENT_METER_SMALLER(HttpStatus.BAD_REQUEST, "PresentMeterSmaller"),

    /** Server 에러*/
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ServerError"),

    /**권한 미존재*/
    AUTHORITY_NOT_FOUND(HttpStatus.NOT_FOUND, "AuthorityNotFound"),

    /**유효하지 않은 기간*/
    UNAVAILABLE_PERIOD(HttpStatus.BAD_REQUEST, "UnavailablePeriod");

    /**HttpStatus*/
    private HttpStatus status;

    /**에러 코드*/
    private String code;

    ErrorCode(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }
}
