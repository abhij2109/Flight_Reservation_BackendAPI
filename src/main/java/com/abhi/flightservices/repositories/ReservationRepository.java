package com.abhi.flightservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhi.flightservices.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	
}
