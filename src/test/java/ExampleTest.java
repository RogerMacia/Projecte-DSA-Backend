import edu.upc.backend.EBDBManagerSystem;
import edu.upc.backend.classes.User;
import edu.upc.backend.exceptions.UsernameAlreadyExistsException;
import edu.upc.backend.util.DBUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class ExampleTest {

    private static final Logger log = Logger.getLogger(ExampleTest.class);
    EBDBManagerSystem manager;
    @Before
    public void setUp() throws Exception{
        manager = EBDBManagerSystem.getInstance();
        manager.clear(); // Clear the database before each test
    }

    @After
    public void tearDown() {
        manager.clear(); // Clear the database after each test
    }

    @Test
    public void testRegisterUser() throws SQLException, UsernameAlreadyExistsException {
        manager.registerUser(new User("Pedro1","Pedro","pedro@gmail.com","123456Ab"));
        Assert.assertEquals(1, manager.getUsersListDatabase().size());
    }

    @Test(expected = UsernameAlreadyExistsException.class)
    public void testRegisterExistingUser() throws SQLException, UsernameAlreadyExistsException {
        manager.registerUser(new User("Pedro2","Pedro","pedro@gmail.com","123456Ab"));
        manager.registerUser(new User("Pedro2","Pedro","pedro@gmail.com","123456Ab")); // This should throw UsernameAlreadyExistsException
    }


    @Test
    public void testIDs()
    {
        int zero = DBUtils.retrieveUserID();
        int one = DBUtils.retrieveUserID();

        log.info(zero);
        log.info(one);
        Assert.assertEquals(0,zero);
        Assert.assertEquals(1,one);
    }

}
