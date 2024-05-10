package com.dino.hotel.api.rate.command.domain;

import com.dino.hotel.api.common.entity.BaseEntity;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.util.VerifyUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomTypeRate extends BaseEntity implements Persistable<RoomTypeRateId> {

    @EmbeddedId
    private RoomTypeRateId id;

    @MapsId("hotelId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    private Integer rate;

    public static RoomTypeRate of(RoomTypeRateId id, Hotel hotel, Integer rate){
        VerifyUtil.verifyNull(id, "id");
        VerifyUtil.verifyNull(hotel, "hotel");
        VerifyUtil.verifyNegative(rate, "rate");

        return new RoomTypeRate(id, hotel, rate);
    }

    @Override
    public boolean isNew() {
        return this.createTime == null;
    }

    @Override
    public RoomTypeRateId getId() {
        return id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Integer getRate() {
        return rate;
    }
}
