package com.abhi.flightservices.integration;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@CrossOrigin
public class ReservationRestController {

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	PassengerRepository passengerRepository;

	@Autowired
	ReservationRepository reservationRepository;
 
	//To see flights according to the given parameters.
	
	@RequestMapping(value = "/flights", method = RequestMethod.GET)
	public List<Flight> findFlights(@RequestParam("from") String from,
									@RequestParam("to") String to,
									@RequestParam("departureDate") @DateTimeFormat(pattern="MM-dd-yyyy") Date departureDate) {
		return flightRepository.findFlights(from, to, departureDate);
	}

	//To see the particular flight from its ID.
	
	@RequestMapping(value = "/flights/{id}")
	public Flight findFlightById(@PathVariable("id") int id) {
		return flightRepository.findById(id).get();
	}

	//To book a reservation of a particular passenger.
	
	@RequestMapping(value = "/reservations", method = RequestMethod.POST)
	@Transactional
	public Reservation saveReservation(@RequestBody CreateReservationRequest request) {

		Flight flight = flightRepository.findById(request.getFlightId()).get();

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
		reservation.setCheckedIn(false);
		return reservationRepository.save(reservation);
	}

	//To see a particular reservation from its ID.
	
	@RequestMapping(value = "/reservations/{id}")
	public Reservation findReservation(@PathVariable("id") int id) {
		return reservationRepository.findById(id).get();
	}

	//To update a booked reservation by its ID.
	
	@RequestMapping(value = "/reservations", method = RequestMethod.PUT)
	public Reservation updateReservation(@RequestBody UpdateReservationRequest request) {
		Reservation reservation = reservationRepository.findById(request.getId()).get();
		reservation.setNumberOfBags(request.getNumberOfBags());
		reservation.setCheckedIn(request.isCheckedIn());

		return reservationRepository.save(reservation);
	}
}
