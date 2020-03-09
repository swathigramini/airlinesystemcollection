package com.capgemini.airlinereservationsystem1.validation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	public static boolean isStringAlphabet(String str) {
      return ((str != null) && (!str.equals("")) && (!str.equals("")) && (str.matches("^[a-zA-Z]*$")));

	}
	public static boolean numValidate(String num) {
		if(num.matches("[0-9]+")) {
			return true;
			}
		return false;
	}
public Integer validateId(String id) {
		Pattern pat = Pattern.compile("\\d+");
		Matcher mat = pat.matcher(id);
		if (mat.matches()) {
			return Integer.parseInt(id);
		} else {
			return null;
		}
	}


	public static boolean isEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null) {
			return false;
		} else {

			return pat.matcher(email).matches();
		}

	}

	public static boolean isPassword(String password) {
		String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
		Pattern pat = Pattern.compile(passwordPattern);
		if (password == null) {
			return false;
		} else {
			return pat.matcher(password).matches();
		}
	}
	public static boolean phoneValidation(CharSequence contact) {

		String phoneRegex = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$";

		Pattern pattern = Pattern.compile(phoneRegex);
		if (contact == null) {
			return false;
		}
		return pattern.matcher(contact).matches();
	}
 public static boolean isName(String txt) {
	 String regx = "[a-zA-Z]+\\.?";
	 Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
	 Matcher matcher= pattern.matcher(txt) ;
	 if(txt==null) {
		 return false;
	 }
	return matcher.find();
 }
 
public static boolean dateValidation(String d) {
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	sdf.setLenient(false);
	try {
		Date date = sdf.parse(d);
		Date today = new Date();
		return date.after(today);
	}catch(Exception e){
		return true;
	}
	
}
	/*public Long validateContact(String contact) {
		Pattern pat = Pattern.compile("\\d{10}");
		Matcher mat = pat.matcher(contact);
		if (mat.matches()) {
			return Long.parseLong(contact);
		} else {
			return null;
		}
	}
*/
	public static boolean validatePassenger(int userId) {
		String passId = Integer.toString(userId);
		if(passId.length() == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean validateAdmin(int adminId) {
		String Id = Integer.toString(adminId);
		if(Id.length()==3) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean validateManager(int managerId) {
		String Id = Integer.toString(managerId);
		if(Id.length()==3) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean validatePhoneNumber(long phoneNumber) {
		String passId = Long.toString(phoneNumber);
		if(passId.length() == 10) {
			return true;
		} else {
			return false;
		}
	}
}
