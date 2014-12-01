/***************************************************************************
 * File name: LoginServlet.java
 * Created by: Assaf Grimberg.
 * Description: This servlet will handle login and register requests
 * Change log:
 * [+] 29/11/2014 - Assaf Grimberg, file created.
 ***************************************************************************/

package forsale.server.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import forsale.server.utils.UserInfo;
import redis.clients.jedis.Jedis;

import forsale.server.utils.Log;


@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/register"})
public class LoginServlet extends ForsaleServlet {


    /*****************************************
     * Constants
     *****************************************/
    private final String HOSTNAME = "localhost";

    /*****************************************
     * Data members
     *****************************************/
    private Jedis m_Jedis;

    /*****************************************
     * Public methods
     *****************************************/

    public LoginServlet() {
        m_Jedis = new Jedis(HOSTNAME);
    }

    /*****************************************
     * Protected methods
     *****************************************/

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log.debug(Log.getMethodName(), "POST request");
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log.debug(Log.getMethodName(), "GET request");
        handleRequest(request, response);
    }

    /*****************************************
     * Private methods
     *****************************************/

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the servlet path
        String path = request.getServletPath();

        Log.debug(Log.getMethodName(), "Path: " + path);

        if (path.equals("register")) {
            register(request, response);
        } else {
            login(request, response);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Log.debug(Log.getMethodName(), "Request for Registration");

        // build user register info
        UserInfo userInfo = new UserInfo();
        String username = request.getParameter("username");
        String password = request.getParameter("password"); // MD5
        userInfo.setCredentials(username, password);
        userInfo.setEmail(request.getParameter("email"));
        userInfo.setFirstName(request.getParameter("fname"));
        userInfo.setLastName(request.getParameter("lname"));
        userInfo.setAge(request.getParameter("age"));
        userInfo.setGender(request.getParameter("gender"));
        Log.debug(Log.getMethodName(), "User info: " + userInfo.toString());

        // add the new user
        String errorMessage = "";
        int newUserId = addNewUser(userInfo, errorMessage);
        if (newUserId < 0) {
            Log.error(Log.getMethodName(), "Failed to register new user: " + errorMessage);
            String jsonErrorString = toJsonError("Failed to register new user: " + errorMessage);
            writeResponse(response, jsonErrorString);
        } else {
            Log.debug(Log.getMethodName(), "Register: username=" + username + ", user_id=" + newUserId);
            writeResponse(response, toJsonOk(newUserId));
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Log.debug(Log.getMethodName(), "Request for Login");

        // build user credentials
        String username = request.getParameter("username");
        String password = request.getParameter("password"); // MD5
        UserInfo.UserCredentials credentials = new UserInfo.UserCredentials();
        credentials.setUsername(username);
        credentials.setPassword(password);

        // sign in existing user
        String errorMessage = "";
        int existingUserId = signInExistingUser(credentials, errorMessage);
        if (existingUserId < 0) {
            Log.error(Log.getMethodName(), "Failed to login existing user: " + errorMessage);
            String jsonErrorString = toJsonError("Failed to login existing user: " + errorMessage);
            writeResponse(response, jsonErrorString);
        } else {
            Log.debug(Log.getMethodName(), "Login: username=" + username + ", user_id=" + existingUserId);
            increaseUserLoginCounterInRedis(existingUserId);
            writeResponse(response, toJsonOk(existingUserId));
        }
    }

    private void increaseUserLoginCounterInRedis(int userId) {
        // TODO - increase user entrance counter, and print count to log
    }

    private int addNewUser(UserInfo userInfo, String errorMessage) {
        return 0; // TODO - insert new user to MySQL db
    }

    private int signInExistingUser(UserInfo.UserCredentials credentials, String errorMessage) {
        return 0; // TODO - find username and password in MySQL db and return user_id
    }
}
