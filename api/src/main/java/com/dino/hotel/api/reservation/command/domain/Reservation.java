package com.dino.hotel.api.reservation.command.domain;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.RoomType;
import com.dino.hotel.api.member.command.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.dino.hotel.api.util.VerifyUtil.verifyDateBetween;
import static com.dino.hotel.api.util.VerifyUtil.verifyNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    private RoomType roomType;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private Reservation(Hotel hotel,
                       Member member,
                       RoomType roomType,
                       LocalDateTime startDate,
                       LocalDateTime endDate,
                       ReservationStatus status) {
        this.hotel = hotel;
        this.member = member;
        this.roomType = roomType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public static Reservation of(Hotel hotel,
                                 Member member,
                                 RoomType roomType,
                                 LocalDateTime startDate,
                                 LocalDateTime endDate,
                                 ReservationStatus status){
        verifyNull(hotel, "hotel");
        verifyNull(member, "member");
        verifyNull(roomType, "roomType");
        verifyDateBetween(startDate, endDate);
        verifyNull(status, "status");

        return new Reservation(hotel, member, roomType, startDate, endDate, status);
    }
}
