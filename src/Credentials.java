public class Credentials {
    private String email;
    private String password;
    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String toString() {
        return "Email: " + email + "\nPassword: " + password;
    }

    public boolean equals(Credentials credentials) {
        return this.email.equals(credentials.getEmail()) && this.password.equals(credentials.getPassword());
    }

}
