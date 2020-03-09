package com.capgemini.airlinereservationsystem1.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.capgemini.airlinereservationsystem1.bean.Admin;
import com.capgemini.airlinereservationsystem1.bean.Manager;
import com.capgemini.airlinereservationsystem1.exception.AdminException;
import com.capgemini.airlinereservationsystem1.exception.InputMismatchException;
import com.capgemini.airlinereservationsystem1.exception.PassengerException;
import com.capgemini.airlinereservationsystem1.service.AdminService;
import com.capgemini.airlinereservationsystem1.service.AdminServiceImpl;
import com.capgemini.airlinereservationsystem1.service.ManagerService;
import com.capgemini.airlinereservationsystem1.service.ManagerServiceImpl;
import com.capgemini.airlinereservationsystem1.validation.Validator;

public class AdminController {
	static Scanner scanner = new Scanner(System.in);
	static AdminService serviceAdmin = new AdminServiceImpl();
	static ManagerService serviceManager = new ManagerServiceImpl();
	static Validator validator = new Validator();
	static Manager manager = new Manager();
	static Admin admin = new Admin();

	public static Admin adminLogin() throws AdminException, PassengerException {
		int adminId = 0;
		
		try {
			System.out.println("Enter Admin Id :");
			char xx = 'y';
			while (xx == 'y') {
				try {
				adminId = scanner.nextInt();
				if (Validator.validateAdmin(adminId)) {
					xx = 'n';
				} else {
					System.err.println("invalid credentials");
				}
				} catch (InputMismatchException e) {
					System.out.println("Enter Only Integer Values");
				}
			}
			admin.setAdminId(adminId);
			scanner.nextLine();
			
			System.out.println("Enter Admin Password :");
			String password = null;
			char c1 = 'y';
			while (c1 == 'y') {
				password = scanner.next();
				if (Validator.isPassword(password)) {
					c1 = 'n';
				} else {
					System.err.println("Enter Correct Password Pattern");
					System.out.println("It should Contain atleast 6 characters(1Caps,1specialsCharacter and 1 number are mandatory)");
				}
			}
			admin.setAdminPassword(password);
			boolean adminLogin1 = serviceAdmin.adminLogin(adminId, password);
			if (adminLogin1) {
				System.out.println("Admin Logged In successfully!!!");
				getAdminFunction();
			} else {
				throw new AdminException("Invalid Username and Password for Admin!!!");
			}
		} catch (AdminException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}// end of adminLogin()

	private static void getAdminFunction() throws PassengerException {
		System.out.println("********************************");
        System.out.println("Welcome to the Admin Page");
        System.out.println("********************************");
		System.out.println("Select a Option");
		System.out.println("Enter Option 1 : Add manager");
		System.out.println("Enter Option 2 : Update manager");
		System.out.println("Enter Option 3 : Display all manager details");
		System.out.println("Enter Option 4 : Delete manager");
		System.out.println("Enter Option 5 : Logout");
		int adminChoice = AirlineReservationSystemController.numValidate(scanner.next());

		switch (adminChoice) {

		case 1:
			try {
				addManager();
			} catch (AdminException e) {
				System.out.println("admin already exists!!!!");
			}
			break;

		case 2:
			updateManager();
			break;
		case 3:
			viewManager();
			break;

		case 4:
			deleteManager();
			break;

		case 5:
			String a[] = new String[] { "1" };
			AirlineReservationSystemController.main(a);
			break;

		default:
			System.out.println("Invalid choice.........");
			break;
		}// end of switch case
	}

	public static void addManager() throws AdminException, PassengerException {

		try {
			int managerId = 0;
			while (true) {
				System.out.println("Enter the Manager Id :");
				Integer sId = validator.validateId(scanner.next());
				if (sId != null) {
					managerId = sId;
					manager.setManagerId(managerId);
					break;
				} else {
					System.out.println("Manager Id should be only an Integer.....");
				}
			}

			boolean addFlighCheck = serviceAdmin.addFlightCheck(managerId);
			if (addFlighCheck) {
				System.out.println("This Manager id is accessing some other Flight!!!");
				System.out.println("Please Enter the different Manager Id :");
				int managerId1 = 0;
				while (true) {
					System.out.println("Enter another Manager id :");
					Integer sId = validator.validateId(scanner.next());
					if (sId != null) {
						managerId1 = sId;
						manager.setManagerId(managerId1);
						break;
					} else {
						System.out.println("Id should be only an Integer...");
					}
				}
			}

			System.out.println("Enter Manager Name :");
			String name = null;
			char ch1 = 'y';
			while (ch1 == 'y') {
				name = scanner.next();
				if (Validator.isName(name)) {
					ch1 = 'n';
				} else {
					System.err.println("invalid credentials!!!");
				}
			}
			manager.setManagerName(name);

			System.out.println("Enter Manager Password :");
			String password = null;
			char c1 = 'y';
			while (c1 == 'y') {
				password = scanner.next();
				if (Validator.isPassword(password)) {
					c1 = 'n';
				} else {
					System.err.println("It should Contain atleast 6 characters(1Caps,1specialsCharacter and 1 number are mandatory)\"");
				}
			}
			manager.setManagerPassword(password);
			System.out.println("Enter Manager EmailId :");
			String email = null;
			char ch = 'y';
			while (ch == 'y') {
				email = scanner.next();
				if (Validator.isEmail(email)) {
					ch = 'n';
					
				} else {
					System.err.println("Enter correct Email Pattern!!!!");
				}
			}
			manager.setManagerEmail(email);

			try {
				System.out.println("Enter Manager contact number :");
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
				admin.setAdminMobile(phoneNumber);
			} catch (Exception e) {
				System.out.println("Enter only Integer value");
			}
			scanner.nextLine();
			

			boolean manager2 = serviceAdmin.addManager(manager);
			if (manager2) {
				System.out.println("Manager Record Inserted!!");
				System.out.println("Enter Option 5 : *To go back*");
				int option = scanner.nextInt();
				if (option == 5) {
					getAdminFunction();
				}
			} else {
				throw new AdminException(" Manager Record is already present....");
			}

		} catch (AdminException e) {
			System.out.println(e.getMessage());
		}
	}// end of add manager()

	public static void deleteManager() throws PassengerException {
		try {

			int managerIdDelete = 0;
			while (true) {
				System.out.println("Enter Manager id you want to delete :");
				Integer sId = validator.validateId(scanner.next());
				if (sId != null) {
					managerIdDelete = sId;
					break;
				} else {
					System.out.println("Id should be only an Integer!!!");
				}
			}
			boolean deleteManager = serviceAdmin.deleteManager(managerIdDelete);
			if (deleteManager) {
				System.out.println("Manager record deleted!!!");
				System.out.println("Enter Option 5 : *To go back*");
				int option = scanner.nextInt();
				if (option == 5) {
					getAdminFunction();
				}

			} else {
				throw new AdminException("Record is not present!!!");
			}

		} catch (AdminException e) {
			System.out.println(e.getMessage());
		}

	}// end of delete manager

	public static void updateManager() throws PassengerException {
		try {
			int managerId = 0;
			while (true) {
				System.out.println("Enter the manager id :");
				Integer sId = validator.validateId(scanner.next());
				if (sId != null) {
					managerId = sId;
					manager.setManagerId(managerId);
					break;
				} else {
					System.out.println("Id should be only an Integer");
				}
			}
			boolean addFlighCheck = serviceAdmin.updateFlightCheck(managerId);
			if (addFlighCheck) {
				System.out.println("Enter manager name :");
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
				manager.setManagerName(name);
				System.out.println("Enter manager password :");
				String password = null;
				char c1 = 'y';
				while (c1 == 'y') {
					password = scanner.next();
					if (Validator.isPassword(password)) {
						c1 = 'n';
					} else {
						System.err.println("It should Contain atleast 6 characters(1Caps,1specialsCharacter and 1 number are mandatory)\"");
					}
				}
				manager.setManagerPassword(password);
				System.out.println("Enter manager emailId :");
				String email = null;
				char ch = 'y';
				while (ch == 'y') {
					email = scanner.next();
					if (Validator.isEmail(email)) {
						ch = 'n';
					} else {
						System.err.println("enter correct email pattern");
					}
				}
				manager.setManagerEmail(email);

				System.out.println("Enter manager contact number :");
				manager.setManagerContact(scanner.nextLong());

				boolean addManager = serviceAdmin.addManager(manager);
				if (addManager) {
					System.out.println("record updated");
					System.out.println("Enter Option 5 : *To go back*");
					int option = scanner.nextInt();
					if (option == 5) {
						getAdminFunction();
					}
				} else {
					throw new AdminException("Record is already present");
				}
			}

		} catch (AdminException e) {
			System.out.println(e.getMessage());
		}

	}// end of update manager()

	public static void viewManager() throws PassengerException {
		List<Manager> managerData = new LinkedList<>();
		managerData = serviceAdmin.viewManager();
		if (managerData == null) {
			System.out.println("No records present");
		} else {
			for (Manager manager : managerData) {
				System.out.println("Manager id= " + manager.getManagerId());
				System.out.println("manager name= " + manager.getManagerName());
				System.out.println("manager contact =" + manager.getManagerContact());
				System.out.println("manager email =" + manager.getManagerEmail());
				System.out.println("-----------------------------------------------");
			}
			System.out.println("Enter Option 5 : *To go back*");
			int option = scanner.nextInt();
			if (option == 5) {
				getAdminFunction();
			}
		}
	}// end of all manager details

}
