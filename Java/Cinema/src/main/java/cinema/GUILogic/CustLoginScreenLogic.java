package cinema.GUILogic;

import cinema.Cinema;
import cinema.Customer;

public class CustLoginScreenLogic {

    public static boolean checkCredentials(String username, String password)  {
        if(Cinema.getCustomer(username) != null){
            if(Cinema.getCustomer(username).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

}
