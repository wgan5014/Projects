/*
* Class handling the checks required to create an account
*
* Assume there exists a class Cinema (similar to ATM.java in asm1)
* which has the loaded accounts in the movie db
*/

package cinema.GUILogic;

import java.util.ArrayList;
import java.util.HashMap;

import cinema.Cinema;
import cinema.Customer;

public class CustRegoScreenLogic {

	// generate a new username based on the current one given
	// appends a random 3 digit number to the end of the username
	public static String generateNewUser(String username) {
		// user is a new string with the same contents as username
		String user = new String(username);
		int numgen; // the random number generated to append to end of existing username to create a new one

		// while the username is a key, continually generate a new random username
		// when one isn't found. this can be given as a suggested username
		while ( Cinema.getCustomer(user) != null) {
			numgen = (int) Math.round(Math.random()*1000);
			user = String.format("%s%d", username, numgen);
		}
		return user;
	}
	
	// check if inputs are valid to create account or not
	public static int createAccountChecks(String user, String pwd, String pwdAgain) {
		// null checks:
		
		// if the user is null
		if (user == null || user.strip().equals("")) { return 1; }
		// pwd null
		else if (pwd == null || pwd.equals("")) { return 2; }
		// pwd re-entry null
		else if (pwdAgain == null || pwdAgain.equals("")) { return 3; }

		if (!(pwd.equals(pwdAgain))) { return 4; }
		
		// accountsAndPwds is a hashMap mapping the string username to the string pwds
		// user already in system
		if (Cinema.getCustomer(user) != null) { return 5; }
		
		// pwds don't match
		// if (!(pwd.equals(pwdAgain))) { return 5; }
		// nothing's null, user not in system, and matching password

		return 0;
	}
	
	// create the account
	public static void createAccount(String username, String pwd) {
		// pass it off to the CustomreRecords class to add it to the db
		Cinema.addAccount( new Customer(username, pwd) );
	}
}
