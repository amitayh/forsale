package forsale.server.domain;


public class User {

    public static class Credentials {

        private Email email;

        private Password password;

        public Credentials() {
            this.email = null;
            this.password = null;
        }

        public Credentials(Email email, Password password) {
            this.email = email;
            this.password = password;
        }

        public Email getEmail() {
            return email;
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Password getPassword() {
            return password;
        }

        public void setPassword(Password password) {
            this.password = password;
        }
    }

    private int id;

    private Credentials credentials;

    private String name;

    private Gender gender;

    private BirthDate birthDath;

    public User() {
        this.credentials = new Credentials();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Email getEmail() { return this.credentials.getEmail(); }

    public void setEmail(Email email) { this.credentials.setEmail(email); }

    public Password getPassword() { return this.credentials.getPassword(); }

    public void setPassword(Password password) { this.credentials.setPassword(password); }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Gender getGender() { return gender; }

    public void setGender(Gender gender) { this.gender = gender; }

    public BirthDate getBirthDath() { return birthDath; }

    public void setBirthDath(BirthDate birthDath) { this.birthDath = birthDath; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

}
