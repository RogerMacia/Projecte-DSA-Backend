import edu.upc.backend.classes.User;
import edu.upc.backend.database.UserDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;

public class DAOTest {

    Logger log = Logger.getLogger(DAOTest.class);
    UserDAO _users;

    @Before
    public void Init()
    {
        _users = UserDAO.getInstance();
    }
    @After
    public void Finalize()
    {
        _users = null;
    }

    /*
    @Test
    public void addUserTest() throws Exception {
        int id = _users.addUser("Daniel","123456Ab","Dani","daniel@gmail.com");
    }

     */

    /*@Test
    public void getUsersTest() throws Exception {
        List<User> userList = _users.getUsers();

        for(User user : userList)
        {
            log.info(user.toString());
        }
    }*/

    @Test
    public void getUserTest() throws Exception {
        User user = _users.getUserById(3);

        log.info(user.toString());

    }
    @Test
    public void updateUserTest() throws Exception {
        int id = 3;
        //_users.updateUser(3,"Carlos","udsuhaiha","Carlosxd","carlos@gmail.com");

        User user = _users.getUserById(3);
        log.info(user.toString());

    }

    @Test
    public void deleteUserTest() throws Exception {
        int id = 4;
        _users.deleteUser(4);


    }

    @Test
    public void getUserByUsernameTest() throws SQLException {
        String username = "Carlosxd";
        User res = _users.getUserByUsername(username);
        log.info(res.toString());
    }


}
