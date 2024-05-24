package com.dino.hotel.api.room.command.application.dto;

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
public class RoomUpdateDto {
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
    private boolean isAvailable;

    public static RoomUpdateDto of(Long roomTypeId,
                          Integer floor,
                          Integer number,
                          String name,
                          boolean isAvailable){
        VerifyUtil.verifyPositive(roomTypeId, "roomTypeId");
        VerifyUtil.verifyPositive(floor, "floor");
        VerifyUtil.verifyPositive(number, "number");
        VerifyUtil.verifyText(name, "name");

        return new RoomUpdateDto(roomTypeId, floor, number, name, isAvailable);
    }
}
