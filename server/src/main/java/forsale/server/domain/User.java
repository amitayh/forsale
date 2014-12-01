package forsale.server.domain;

import java.util.Date;

public class User {

    public static class Credentials {

        private Email email;

        private String password;

        public Credentials() {}

        public Credentials(Email email, String password) {
            this.email = email;
            this.password = password;
        }

        public Email getEmail() {
            return email;
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private int id;

    /*
    private Email email;
    private String password;
    */
    private Credentials credentials;

    private String name;

    private Gender gender;

    private Date birthDath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Email getEmail() {
        //return email;
        return this.credentials.getEmail();
    }

    public void setEmail(Email email) {
        //this.email = email;
        this.credentials.setEmail(email);
    }

    public String getPassword() {
        //return password;
        return this.credentials.getPassword();
    }

    public void setPassword(String password) {
        //this.password = password;
        this.credentials.setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDath() {
        return birthDath;
    }

    public void setBirthDath(Date birthDath) {
        this.birthDath = birthDath;
    }
    
}
