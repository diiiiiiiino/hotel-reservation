package com.dino.hotel.api.hotel.command.application.dto;

import com.dino.hotel.api.hotel.command.domain.Address;
import com.dino.hotel.api.util.VerifyUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HotelUpdateDto {
    @Positive
    @NotNull
    private Long id;
    @NotNull
    private Address address;
    @NotBlank
    private String name;

    public static HotelUpdateDto of(Long id, Address address, String name){
        VerifyUtil.verifyNegative(id, "id");
        VerifyUtil.verifyNull(address, "address");
        VerifyUtil.verifyText(name, "name");

        return new HotelUpdateDto(id, address, name);
    }
}
