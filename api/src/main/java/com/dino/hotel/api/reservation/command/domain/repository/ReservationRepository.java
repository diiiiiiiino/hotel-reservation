package com.dino.hotel.api.reservation.command.domain.repository;

import com.dino.hotel.api.reservation.command.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
