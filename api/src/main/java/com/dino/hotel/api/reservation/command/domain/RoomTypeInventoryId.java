package com.dino.hotel.api.reservation.command.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomTypeInventoryId implements Serializable {

    private Long hotelId;
    private Long roomTypeId;
    private LocalDateTime date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomTypeInventoryId that = (RoomTypeInventoryId) o;
        return Objects.equals(hotelId, that.hotelId) && Objects.equals(roomTypeId, that.roomTypeId) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, roomTypeId, date);
    }

    public static RoomTypeInventoryId of(Long hotelId, Long roomTypeId, LocalDateTime date){
        return new RoomTypeInventoryId(hotelId, roomTypeId, date);
    }
}
