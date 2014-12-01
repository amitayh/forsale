/***************************************************************************
 * File name: UserInfo.java
 * Created by: Assaf Grimberg.
 * Description: Just a JavaBean class that describes a user info
 * Change log:
 * [+] 29/11/2014 - Assaf Grimberg, file created.
 ***************************************************************************/

package forsale.server.utils;

import java.io.Serializable;

public class UserInfo implements Serializable {

    public static class UserCredentials {
        private String m_Username;
        private String m_Password;

        public void setUsername(String username) {
            m_Username = username;
        }

        public String getUsername() {
            return m_Username;
        }

        public String getPassword() {
            return m_Password;
        }

        public void setPassword(String password) {
            m_Password = password;
        }
    }

    /*****************************************
     * Data members
     *****************************************/

    private UserCredentials m_Credentials;
    private String m_Email;
    private String m_FirstName;
    private String m_LastName;
    private String m_Age;
    private String m_Gender;

    /*****************************************
     * Public methods
     *****************************************/

    public String getUsername() {
        return m_Credentials.getUsername();
    }

    public String getPassword() {
        return m_Credentials.getPassword();
    }

    public void setCredentials(String username, String password) {
        m_Credentials.setUsername(username);
        m_Credentials.setPassword(password);
    }

    public String getEmail() {
        return m_Email;
    }

    public void setEmail(String email) {
        m_Email = email;
    }

    public String getFirstName() {
        return m_FirstName;
    }

    public String getLastName() {
        return m_LastName;
    }

    public String getAge() {
        return m_Age;
    }

    public String getGender() {
        return m_Gender;
    }

    public void setFirstName(String firstName) {
        m_FirstName = firstName;
    }

    public void setLastName(String lastName) {
        m_LastName = lastName;
    }

    public void setAge(String age) {
        m_Age = age;
    }

    public void setGender(String gender) {
        m_Gender = gender;
    }

    @Override
    public String toString() {
        return "Username: " + getUsername() + ";" +
                "Email: " + getEmail() + ";" +
                "First: " + getFirstName() + ";" +
                "Last: " + getLastName() + ";" +
                "Age: " + getAge() + ";" +
                "Gender: " + getGender();
    }
}
