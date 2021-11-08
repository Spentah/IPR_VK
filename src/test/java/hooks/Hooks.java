package hooks;

import api.utils.RequestSpecUtil;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;

public class Hooks {

    @BeforeTest
    public void setUp() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("user.properties"));
        RequestSpecUtil.setSpecification();
    }

    @AfterTest
    public void tearDown(){

    }
}
