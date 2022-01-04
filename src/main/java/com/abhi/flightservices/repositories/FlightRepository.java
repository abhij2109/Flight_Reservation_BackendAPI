package com.abhi.flightservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhi.flightservices.entities.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
	
}
