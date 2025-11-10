import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExampleTest {

    private static final Logger log = Logger.getLogger(ExampleTest.class);
    int a, b, c;
    @Before
    public void setUp() throws Exception{
        a = 8;
        b = 3;
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testArithmetica() throws Exception {
        c = a + b;
        Assert.assertEquals(11, c);
        log.info(String.format("c equals %d", c));
    }
}
