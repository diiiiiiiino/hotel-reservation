package com.dino.hotel.api.reservation.command.repository;

import com.dino.hotel.api.helper.BaseRepositoryTest;
import com.dino.hotel.api.helper.builder.HotelBuilder;
import com.dino.hotel.api.helper.builder.MemberCreateHelperBuilder;
import com.dino.hotel.api.helper.builder.RoomTypeBuilder;
import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.RoomType;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.hotel.command.domain.repository.RoomTypeRepository;
import com.dino.hotel.api.member.command.domain.Member;
import com.dino.hotel.api.member.command.domain.repository.MemberRepository;
import com.dino.hotel.api.reservation.command.domain.Reservation;
import com.dino.hotel.api.reservation.command.domain.ReservationStatus;
import com.dino.hotel.api.reservation.command.domain.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class ReservationRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("Reservation 엔티티 저장")
    void createReservation() {
        Member member = MemberCreateHelperBuilder.builder().build();
        Hotel hotel = HotelBuilder.builder().build();
        RoomType roomType = RoomTypeBuilder.builder().build();

        memberRepository.save(member);
        hotelRepository.save(hotel);
        roomTypeRepository.save(roomType);


        Reservation reservation = Reservation.of(hotel,
                member,
                roomType,
                LocalDateTime.of(2024, 5, 8, 12, 0, 0),
                LocalDateTime.of(2024, 5, 9, 12, 0, 0),
                ReservationStatus.PENDING);

        reservationRepository.save(reservation);

        flushAndClear();
    }
}
