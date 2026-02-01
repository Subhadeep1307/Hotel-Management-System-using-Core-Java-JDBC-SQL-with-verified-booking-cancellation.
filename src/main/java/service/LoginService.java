package service;


import dao.UserDAO;
import model.User;

public class LoginService {

    private UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        return userDAO.validateUser(username, password);
    }
}
