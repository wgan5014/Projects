package cinema.GUILogic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShowingScreenLogic {
    public static boolean isValidTime(String date,String hour,String minute){
        String DateTimeObj = date + " " + hour+":"+minute;
        LocalDateTime dt;
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dt = LocalDateTime.parse(DateTimeObj,formatter);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
