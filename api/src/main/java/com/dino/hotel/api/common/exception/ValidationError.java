package com.dino.hotel.api.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 유효성 검증 실패 내용을 관리하는 클래스
 */
@Getter
@AllArgsConstructor
public class ValidationError {
    private String name;
    private String code;

    public static ValidationError of(String name, String code){
        return new ValidationError(name, code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationError that = (ValidationError) o;
        return name.equals(that.name) && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code);
    }
}
