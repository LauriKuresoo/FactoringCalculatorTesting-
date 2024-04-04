import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TestHelper {

    static WebDriver driver;

    String baseUrl = "https://www.swedbank.lt/business/finance/trade/factoring?language=ENG";
    @Before
    public void setUp(){

        // if you use Chrome:
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\UserDrivers\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(baseUrl);

    }






    @After
    public void tearDown(){
        driver.close();
    }
}
