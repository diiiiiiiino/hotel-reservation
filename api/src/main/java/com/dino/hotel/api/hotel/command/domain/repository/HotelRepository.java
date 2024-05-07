package com.dino.hotel.api.hotel.command.domain.repository;

import com.dino.hotel.api.hotel.command.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
