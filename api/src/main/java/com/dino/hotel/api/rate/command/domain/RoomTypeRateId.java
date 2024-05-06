package com.dino.hotel.api.rate.command.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeRateId implements Serializable {
    private Long hotelId;
    private LocalDateTime dateTime;

    public static RoomTypeRateId of(Long hotelId, LocalDateTime dateTime){
        return new RoomTypeRateId(hotelId, dateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomTypeRateId that = (RoomTypeRateId) o;
        return Objects.equals(hotelId, that.hotelId) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, dateTime);
    }
}
