package dataaccessobject;

import models.User;

public interface UserDAO {
    User login(String username, String password);
    boolean register(String username, String password);
}
