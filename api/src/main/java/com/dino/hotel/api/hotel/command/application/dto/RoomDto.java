package com.dino.hotel.api.hotel.command.application.dto;

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
public class RoomDto {
    @Positive
    @NotNull
    private Long roomTypeId;

    @Positive
    @NotNull
    private Integer floor;

    @Positive
    @NotNull
    private Integer number;

    @NotBlank
    private String name;

    public static RoomDto of(
            Long roomTypeId,
            Integer floor,
            Integer number,
            String name){
        VerifyUtil.verifyNegative(roomTypeId, "roomTypeId");
        VerifyUtil.verifyNegative(floor, "floor");
        VerifyUtil.verifyNegative(number, "number");
        VerifyUtil.verifyText(name, "name");

        return new RoomDto(roomTypeId, floor, number, name);
    }
}
