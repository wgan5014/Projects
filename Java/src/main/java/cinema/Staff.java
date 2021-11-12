package cinema;

public class Staff {
    boolean isManager;
    String username;
    String password;

    public Staff(boolean isManager, String username, String password) {
        this.isManager = isManager;
        this.username = username;
        this.password = password;
    }

    public boolean isManager() {
        return isManager;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
