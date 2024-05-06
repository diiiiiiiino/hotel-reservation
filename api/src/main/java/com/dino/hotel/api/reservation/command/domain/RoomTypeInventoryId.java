package com.dino.hotel.api.reservation.command.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeInventoryId implements Serializable {

    private Long hotelId;
    private Long roomTypeId;
    private LocalDateTime date;

}
