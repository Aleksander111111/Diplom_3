package utils;

public class UserCredentials {
    public String email;
    public String password;
    public String name;

    public UserCredentials(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserCredentials(String email, String password) {
        this(email, password, null);
    }
}