package lk.ijse.chatApplication.model;

public class Log {
    private String username;

    public Log() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Log(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "log{" +
                "username='" + username + '\'' +
                '}';
    }
}
