package com.abhi.flightservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhi.flightservices.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
	
}
