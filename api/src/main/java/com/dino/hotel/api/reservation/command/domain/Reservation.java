package com.dino.hotel.api.reservation.command.domain;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.Room;
import com.dino.hotel.api.member.command.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;

    @OneToOne
    private Member member;

    private String roomTypeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private Reservation(Hotel hotel,
                       Member member,
                       String roomTypeId,
                       LocalDateTime startDate,
                       LocalDateTime endDate,
                       ReservationStatus status) {
        this.hotel = hotel;
        this.member = member;
        this.roomTypeId = roomTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public static Reservation of(Hotel hotel,
                                 Member member,
                                 String roomTypeId,
                                 LocalDateTime startDate,
                                 LocalDateTime endDate,
                                 ReservationStatus status){
        return new Reservation(hotel, member, roomTypeId, startDate, endDate, status);
    }
}
