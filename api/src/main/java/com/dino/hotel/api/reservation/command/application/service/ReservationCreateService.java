package com.dino.hotel.api.reservation.command.application.service;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import com.dino.hotel.api.hotel.command.domain.RoomType;
import com.dino.hotel.api.hotel.command.domain.exception.HotelNotFoundException;
import com.dino.hotel.api.hotel.command.domain.repository.HotelRepository;
import com.dino.hotel.api.member.command.domain.Member;
import com.dino.hotel.api.reservation.command.application.dto.ReservationDto;
import com.dino.hotel.api.reservation.command.domain.Reservation;
import com.dino.hotel.api.reservation.command.domain.ReservationStatus;
import com.dino.hotel.api.reservation.command.domain.RoomTypeInventory;
import com.dino.hotel.api.reservation.command.domain.exception.NotFoundRoomsAvailableForReservation;
import com.dino.hotel.api.reservation.command.domain.repository.ReservationRepository;
import com.dino.hotel.api.reservation.command.domain.repository.RoomTypeInventoryRepository;
import com.dino.hotel.api.room.command.domain.exception.RoomNotFoundException;
import com.dino.hotel.api.util.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationCreateService {
    private final HotelRepository hotelRepository;
    private final RoomTypeInventoryRepository roomTypeInventoryRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public void create(Member member, ReservationDto reservationDto) {
        VerifyUtil.verifyNull(reservationDto, "reservationDto");

        Hotel hotel = hotelRepository.findById(reservationDto.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

        if(!hotel.hasRoom(reservationDto.getRoomId()))
            throw new RoomNotFoundException("Room not found");

        List<RoomTypeInventory> roomTypeInventories = roomTypeInventoryRepository.findAllByStartAndEnd(reservationDto.getHotelId(),
                                                                                                       reservationDto.getRoomTypeId(),
                                                                                                       reservationDto.getStartDate(),
                                                                                                       reservationDto.getEndDate());
        long untilDay = reservationDto.startUntilEnd(ChronoUnit.DAYS);
        if(untilDay != roomTypeInventories.size()){
            throw new NotFoundRoomsAvailableForReservation("Not found rooms available for reservation");
        }

        Integer numberOfRoomsToReserve = reservationDto.getNumberOfRoomsToReserve();
        for(RoomTypeInventory inventory : roomTypeInventories){
            inventory.reserve(numberOfRoomsToReserve);
        }

        Reservation reservation = Reservation.of(
                hotel,
                member,
                RoomType.of(reservationDto.getRoomTypeId()),
                reservationDto.getStartDate(),
                reservationDto.getEndDate(),
                ReservationStatus.PENDING);

        reservationRepository.save(reservation);
    }
}
