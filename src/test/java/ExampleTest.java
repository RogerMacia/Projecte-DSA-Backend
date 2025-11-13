import edu.upc.backend.EETACBROSMannagerSystemImpl;
import edu.upc.backend.classes.User;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExampleTest {

    private static final Logger log = Logger.getLogger(ExampleTest.class);
    EETACBROSMannagerSystemImpl manager;
    @Before
    public void setUp() throws Exception{
        manager = EETACBROSMannagerSystemImpl.getInstance();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUsuari() throws Exception {
        manager.addUser(new User("Pedro1","Pedro","pedro@gmail.com","123456Ab"));
        manager.addUser(new User("Pedro2","Pedro","pedro@gmail.com","123456Ab"));
        Assert.assertEquals(manager.getUsersList().size(), 2);

        //log.info(String.format("c equals %d", c));
    }
}
