import edu.upc.backend.EBDBManagerSystem;
import edu.upc.backend.classes.User;
import edu.upc.backend.util.DBUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExampleTest {

    private static final Logger log = Logger.getLogger(ExampleTest.class);
    EBDBManagerSystem manager;
    @Before
    public void setUp() throws Exception{
        manager = EBDBManagerSystem.getInstance();
    }

    @After
    public void tearDown() {
    }

    /*
    @Test
    public void testUsuari() throws Exception {
        manager.registerUser(new User("Pedro1","Pedro","pedro@gmail.com","123456Ab"));
        manager.registerUser(new User("Pedro2","Pedro","pedro@gmail.com","123456Ab"));
        //Assert.assertEquals(manager.getUsersList().size(), 2);

        //log.info(String.format("c equals %d", c));
    }

     */

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
