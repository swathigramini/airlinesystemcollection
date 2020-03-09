package com.capgemini.airlinereservationsystem1.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.capgemini.airlinereservationsystem1.bean.Availability;
import com.capgemini.airlinereservationsystem1.bean.Flight;
import com.capgemini.airlinereservationsystem1.bean.Passenger;
import com.capgemini.airlinereservationsystem1.bean.Ticket;
import com.capgemini.airlinereservationsystem1.exception.AdminException;
import com.capgemini.airlinereservationsystem1.exception.InputMismatchException;
import com.capgemini.airlinereservationsystem1.exception.ManagerException;
import com.capgemini.airlinereservationsystem1.exception.PassengerException;
import com.capgemini.airlinereservationsystem1.exception.TicketException;
import com.capgemini.airlinereservationsystem1.service.ManagerService;
import com.capgemini.airlinereservationsystem1.service.ManagerServiceImpl;
import com.capgemini.airlinereservationsystem1.service.PassengerService;
import com.capgemini.airlinereservationsystem1.service.PassengerServiceImpl;
import com.capgemini.airlinereservationsystem1.validation.Validator;

public class PassengerController {
	static Validator validator = new Validator();
	static PassengerService servicePassenger = new PassengerServiceImpl();
	static ManagerService serviceManager = new ManagerServiceImpl();
	static Scanner scanner = new Scanner(System.in);
	static Passenger passenger = new Passenger();
	static Ticket ticket = new Ticket();
	static int passengerId;
	static int ticketId;

	public static Passenger registerPassenger() {
		
		try {
			System.out.println("Enter passenger Id :");
			int userId = 0;
			char xx = 'y';
			while (xx == 'y') {
				try {
				userId = scanner.nextInt();
				if (Validator.validatePassenger(userId)) {
					xx = 'n';
				} else {
					System.err.println("invalid credentials");
				}
				} catch (InputMismatchException e) {
					System.out.println("Enter Only Integer Values");
				}
			}
			passenger.setPassengerId(userId);
		} catch (Exception e) {
			System.out.println("Enter only Integer value");
		}
		scanner.nextLine();
		
		try {
			System.out.println("Enter passenger name :");
			String name = null;
			char ch1 = 'y';
			while (ch1 == 'y') {
				name = scanner.next();
				if (Validator.isName(name)) {
					ch1 = 'n';
				} else {
					System.err.println("invalid credentials");
				}
			}
			passenger.setPassengerName(name);
			System.out.println("Enter passenger password :");
			String password = null;
			char c1 = 'y';
			while (c1 == 'y') {
				password = scanner.next();
				if (Validator.isPassword(password)) {
					c1 = 'n';
				} else {
					System.err.println("It should Contain atleast 6 characters");
				}
			}
			passenger.setPassengerPassword(password);
			
			
			try {
				System.out.println("Enter passenger contact number :");
				Long phoneNumber=null;
				char yy = 'y';
				while (yy == 'y') {
					try {
					phoneNumber = scanner.nextLong();
					if (Validator.validatePhoneNumber(phoneNumber)) {
						yy = 'n';
					} else {
						System.err.println("invalid credentials");
					}
					} catch (InputMismatchException e) {
						System.out.println("Enter Only Integer Values");
					}
				}
				passenger.setPassengerContact(phoneNumber);
			} catch (Exception e) {
				System.out.println("Enter only Integer value");
			}
			scanner.nextLine();
			
			System.out.println("Enter manager emailId :");
			String email = null;
			char ch = 'y';
			while (ch == 'y') {
				email = scanner.next();
				if (Validator.isEmail(email)) {
					ch = 'n';
				} else {
					System.err.println("Enter correct email pattern:");
				}
			}
			
			passenger.setPassengerEmail(email);
			boolean addPassenger = servicePassenger.registerPassenger(passenger);
			if (addPassenger) {
				System.out.println(" passenger record inserted");
				System.out.println("Welcome"+name+"To Airline Managment System");
				System.out.println("Enter 1 to go to the Menu");
				int option = scanner.nextInt();
				if (option == 0) {
					try {
						getPassengerFunction();
					} catch (PassengerException e) {
						System.out.println(e.getMessage());
					}
				}
			} else {
				throw new AdminException("Record is already present");
			}

		} catch (AdminException e) {
			System.out.println(e.getMessage());
		}
		return passenger;
	}// end of add passenger

	static Passenger passengerLogin() throws PassengerException {
		int passengerId = 0;
		try {
			System.out.println("************************");
			System.out.println("Enter passenger id :");
			while (true) {
				Integer sId = validator.validateId(scanner.next());
				if (sId != null) {
					passengerId = sId;
					passenger.setPassengerId(passengerId);
					break;
				} else {
					System.out.println("Id should be Integer");
				}
			}

			System.out.println("Enter passenger password :");
			String password = null;
			char c1 = 'y';
			while (c1 == 'y') {
				password = scanner.next();
				if (Validator.isPassword(password)) {
					c1 = 'n';
				} else {
					System.err.println(
							"It should Contain atleast 6 characters(1Caps,1specialCharacter and 1 number are mandatory)\"");
				}
			}

			passenger.setPassengerPassword(password);

			Passenger passengerLogin = new Passenger();
			passengerLogin = servicePassenger.passengerLogin(passengerId, password);
			if (passengerLogin != null) {
				System.out.println("Login successfully");
				getPassengerFunction();
			} else {
				throw new PassengerException("Invalid username and password for passenger");

			}
		} catch (PassengerException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}// end of passenger login

	private static void getPassengerFunction() throws PassengerException {
		System.out.println("**Welcome to Passenger Menu**");
		System.out.println("Enter the option 1 : View flight details");
		System.out.println("Enter the option 2 : Book ticket");
		System.out.println("Enter the option 3 : View Ticket details");
		System.out.println("Enter the option 4 : Cancel ticket");
		System.out.println("Enter the option 5 : Logout");
		int passengerChoice = AirlineReservationSystemController.numValidate(scanner.next());
		// Flight flightId = new Flight();
		Passenger passengerLogin = new Passenger();

		switch (passengerChoice) {
		case 1:
			viewFlight();
			break;
		case 2:
			bookTicket(passengerLogin);
			break;
		case 3:
			viewTicketDetails();
			break;
		case 4:
			cancelTicket();
			break;
		case 5:
			String a[] = new String[] { "1" };
			AirlineReservationSystemController.main(a);
			break;

		default:
			System.out.println("Invalid choice made");
			break;
		}// end of switch case
	}
	static void viewFlight() throws PassengerException {
		List<Flight> flightData = new LinkedList<>();
		flightData = servicePassenger.viewFlight();
		if (flightData == null) {
			System.out.println("No records present");
		} else {
			for (Flight flight : flightData) {
				System.out.println("flight id= " + flight.getFlightId());
				System.out.println("flight name= " + flight.getFlightName());
				System.out.println("destination= " + flight.getDestination());
				System.out.println("source= " + flight.getSource());
				System.out.println("Total no of seats= " + flight.getTotalNoOfseats());
				System.out.println("Ticket Price= " + flight.getTicketPrice());
				System.out.println("Journey date=" + flight.getDate());
				System.out.println("------------------------------------------------");
			}
			System.out.println("*Enter the option 5:Logout*");
			int option = scanner.nextInt();
			if (option == 5) {
				getPassengerFunction();
			}
		}
	}// end of view flight
	static void viewTicketDetails() throws PassengerException {
		List<Ticket> TicketData = new LinkedList<>();
		TicketData = servicePassenger.viewTicketDetails();
		if (TicketData == null) {
			System.out.println("No records present");
		} else {
			for (Ticket ticket : TicketData) {
				System.out.println("Ticket id="+ticket.getTicketId());
				System.out.println("Source ="+ticket.getSource());
				System.out.println("Destination ="+ticket.getDestination());
				System.out.println("Journey Date ="+ticket.getJourneyDate());
				System.out.println("No of seats Booked ="+ticket.getNoOfSeats());
				System.out.println("------------------------------------------------");
			}
			System.out.println("*Enter the option 5:Logout*");
			int option = scanner.nextInt();
			if (option == 5) {
				getPassengerFunction();

			}
		}
	}
	static void bookTicket(Passenger passengerLogin) throws PassengerException {

		Random random = new Random();

		System.out.println(" Enter flight id you want to book ticket:");
		try {
			int bookFlightId = scanner.nextInt();

			Flight bookFlight = servicePassenger.flightAvailable(bookFlightId);
			if (bookFlight != null) {
				int ticketId = random.nextInt(50);
				int flightId = bookFlight.getFlightId();
				System.out.println("Flight Id is :" + flightId);
				String source = bookFlight.getSource();
				System.out.println("Source :" + source);
				String destination = bookFlight.getDestination();
				System.out.println("Destination :" + destination);
				System.out.println("*********************************");
               
				System.out.println("Enter no of seats you want:");
				int seats = scanner.nextInt();
				System.out.println("Enter the Source:");
				String source1 = scanner.next();
				System.out.println("Enter the destination:");
				String dest = scanner.next();
				System.out.println("Enter date:");
				String journeyDate = null;
				char ch = 'y';
				while (ch == 'y') {
					journeyDate = scanner.next();
					if (Validator.dateValidation(journeyDate)) {
						ch = 'n';
						passenger.setPassengerEmail(journeyDate);
					} else {
						System.err.println("Enter correct date:");
					}
				}
				servicePassenger.bookTicket(ticket);
				System.out.println("ticket booked");
				System.out.println("The Ticket Id is :" + ticketId);
				ticket.setTicketId(ticketId);

				System.out.println("*Enter the option 5:Logout*");
				int option = scanner.nextInt();
				if (option == 5) {
					getPassengerFunction();
				}

			} else {
				throw new TicketException("Flight is not available");
			}
		} catch (TicketException e) {
			System.out.println(e.getMessage());
		}

	}// end of book ticket

	public static void cancelTicket() throws PassengerException {
		try {
			int ticketId = 0;
			while (true) {
				System.out.println("Enter Ticket id you want to cancel:");
				Integer sId = validator.validateId(scanner.next());
				if (sId != null) {
					ticketId = sId;
					break;
				} else {
					System.out.println("Id should be only an Integer");
				}
			}
			boolean ticketCancel = servicePassenger.TicketCancellation(ticketId);
			if (ticketCancel) {
				System.out.println("Ticket Cancelled SuccessFully!!!!");
				System.out.println("*Enter the option 5:Logout*");
				int option = scanner.nextInt();
				if (option == 5) {
					getPassengerFunction();
				}

			} else {
				throw new ManagerException("Record  not present");
			}

		} catch (ManagerException e) {
			System.out.println(e.getMessage());
		}

	}// end of cancelFlight

}// end of cancelTicket