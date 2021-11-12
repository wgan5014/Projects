package cinema;

import cinema.GUILogic.CustRegoScreenLogic;
import cinema.Cinema;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.lang.*;

public class TestCustRegoScreenLogic {
	/*
	@Parameters
	public Collection<Object[]> createAccountChecksData() {
		Object[][] data = new Object[][] {
			{1, null, "", ""}, // usr null
			{1, "", "", ""}, // empty usr
			{2, "a", null, ""}, // null pwd
			{2, "a", "", ""}, // empty pwd
			{3, "a", "a", null}, // null pwdagain
			{3, "a", "a", ""}, // empty pwdagain
			{4, "a", "a", "q"}, // pwd and pwdagain not equal
			{0, "a", "a", "a"} // everything passed
		};
		
		return Arrays.asList(data);
	}*/
	@BeforeAll
	public static void cleanFiles(){
		IO.deleteFiles();
		IO.load();
	}
	
	@AfterEach
	public void reset() {
		Cinema.removeAllCustomers();
	}
	
	@Test
	public void testAccountChecks() {
		//assertEquals(0, CustRegoScreenLogic.createAccountChecks("a", "b", "b"));

		// usrname null | ""
		assertEquals(1,CustRegoScreenLogic.createAccountChecks("", "b", "b"));
		assertEquals(1, CustRegoScreenLogic.createAccountChecks(null, "b", "b"));

		// pwd null | ""
		assertEquals(2, CustRegoScreenLogic.createAccountChecks("a", "", "b"));
		assertEquals(2, CustRegoScreenLogic.createAccountChecks("a", null, "b"));

		// pwdagain null | ""
		assertEquals(3, CustRegoScreenLogic.createAccountChecks("a", "b", null));
		assertEquals(3, CustRegoScreenLogic.createAccountChecks("a", "b", ""));
		
		// pwd no match
		assertEquals(4, CustRegoScreenLogic.createAccountChecks("a", "b", "c"));
		
		// all checks pass --> return status 0
		assertEquals(0, CustRegoScreenLogic.createAccountChecks("a", "b", "b"));
		
	}
	
	@Test
	public void testCreateAccount() {
		// usr, pwd
		CustRegoScreenLogic.createAccount("b", "c");
		Customer result = Cinema.getCustomer("b");
		assertTrue(result != null);
	}
	
	@Test
	public void testAccountExists() {
		Cinema.addAccount(new Customer("b", "b"));
		
		// usr with usrname 'b' already exists!
		assertEquals(5, CustRegoScreenLogic.createAccountChecks("b", "c","c"));
	}
	
	@Test
	public void testGenerateNewUser() {
		Cinema.addAccount(new Customer("b", "b"));
		String genName = CustRegoScreenLogic.generateNewUser("b");
		int num = Integer.parseInt( genName.substring(1, 4) );
		assertTrue(num <= 999 && num >= 100);
	}
}


