package cinema.GUILogic;

import cinema.Cinema;
import cinema.Staff;

public class StaffLoginScreenLogic {
    public static boolean checkCredentials(String username, String password) {
        boolean valid = false;
        for (Staff staff: Cinema.getStaff()) {
            if (staff.getUsername().equals(username) && staff.getPassword().equals(password)) {
                valid = true;
            }
        }
        return valid;
    }
    public static boolean checkManagerCredentials(String usr, String pwd) {
        boolean valid = false;
        for (Staff staff: Cinema.getStaff(true)) {
            if (staff.getUsername().equals(usr) && staff.getPassword().equals(pwd)) {
                valid = true;
            }
        }
        return valid;
    }
}
