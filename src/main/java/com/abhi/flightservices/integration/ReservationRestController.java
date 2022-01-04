package com.abhi.flightservices.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhi.flightservices.dto.CreateReservationRequest;
import com.abhi.flightservices.dto.UpdateReservationRequest;
import com.abhi.flightservices.entities.Flight;
import com.abhi.flightservices.entities.Passenger;
import com.abhi.flightservices.entities.Reservation;
import com.abhi.flightservices.repositories.FlightRepository;
import com.abhi.flightservices.repositories.PassengerRepository;
import com.abhi.flightservices.repositories.ReservationRepository;

@RestController
public class ReservationRestController {

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	PassengerRepository passengerRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@RequestMapping(value = "/flights", method = RequestMethod.GET)
	public List<Flight> findFlights() {
		return flightRepository.findAll();
	}

	@RequestMapping(value = "/flights/{id}")
	public Flight findFlightById(@PathVariable("id") int id) {
		 Flight flight = flightRepository.findById(id).get();
		 return flight;
	}
	
	@RequestMapping(value = "/reservations", method = RequestMethod.POST)
	@Transactional
	public Reservation saveReservation(CreateReservationRequest request) {

		Flight flight = flightRepository.findById(request.getFlightID()).get();

		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setMiddleName(request.getPassengerMiddleName());
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());

		Passenger savedPassenger = passengerRepository.save(passenger);

		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckIn(false);
		return reservationRepository.save(reservation);
	}

	@RequestMapping(value = "/reservations/{id}")
	public Reservation findReservation(@PathVariable("id") int id) {
		return reservationRepository.findById(id).get();
	}

	@RequestMapping(value = "/reservations", method = RequestMethod.PUT)
	public Reservation updateReservation(UpdateReservationRequest request) {
		Reservation reservation = reservationRepository.findById(request.getId()).get();
		reservation.setId(request.getId());
		reservation.setCheckIn(request.isCheckedIn());
		
		return reservationRepository.save(reservation);
	}
}
