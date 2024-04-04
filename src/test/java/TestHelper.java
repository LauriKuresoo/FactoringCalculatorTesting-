import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.channels.Selector;
import java.util.concurrent.TimeUnit;

enum AdvanceRate {
    a75("75"), a80("80"), a85("85"), a90("90");
    public final String label;
    AdvanceRate(String rate) {
        this.label = rate;
    }
}

enum PaymentTerm{
    a30("30"), a60("60"), a90("90"), a120("120");
    public final String label;
    PaymentTerm(String term) {
        this.label = term;
    }
}

public class TestHelper {

    static WebDriver driver;
    String baseUrl = "https://www.swedbank.lt/business/finance/trade/factoring?language=ENG";

    WebElement invoiceAmount;
    WebElement advanceRate;
    Select advanceRateSelector;
    WebElement interestRate;
    WebElement paymentTerm;
    Select paymenttermSelector;
    WebElement commisionFee;
    WebDriverWait wait;


    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\UserDrivers\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);
        wait = new WebDriverWait(driver, 2);

        invoiceAmount = driver.findElement(By.id("D5"));

        advanceRate = driver.findElement(By.id("D6"));
        advanceRateSelector = new Select(advanceRate);

        interestRate = driver.findElement(By.id("D7"));

        paymentTerm = driver.findElement(By.id("D8"));
        paymenttermSelector = new Select(paymentTerm);

        commisionFee = driver.findElement(By.id("D9"));
    }

    void closeCookies(){
        WebElement cookiesAccept = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button ui-cookie-consent__accept-button']")));
        cookiesAccept.click();
    }

    void calculate(){
        WebElement button = driver.findElement(By.id("calculate-factoring"));
        button.click();
    }

    private void invoiceAmountFill(double invoiceAmount){
        this.invoiceAmount.clear();
        this.invoiceAmount.sendKeys(String.valueOf(invoiceAmount));
    }

    private void advanceRateFill(AdvanceRate rate){
        this.advanceRateSelector.selectByValue(rate.label);
    }

    private void interestRateFill(double interestRate){
        this.interestRate.clear();
        this.interestRate.sendKeys(Double.toString(interestRate));
    }

    private void paymentTermFill(PaymentTerm term){
        this.paymenttermSelector.selectByValue(term.label);
    }

    private void commissionFeeFill(double commissionFee){
        this.commisionFee.clear();
        this.commisionFee.sendKeys(Double.toString(commissionFee));
    }


    void fillInputFields(TestSet set){
        invoiceAmountFill(set.getInvoiceAmount());
        advanceRateFill(set.getAdvanceRate());
        interestRateFill(set.getInterestRate());
        paymentTermFill(set.getPaymentTerm());
        commissionFeeFill(set.getCommissionFee());
    }

    double calculatateValidOutput(TestSet set){
        double answer = set.getInvoiceAmount() *
                (Double.parseDouble(set.getAdvanceRate().label)/100)
                * (set.getInterestRate()/100)
                * (Double.parseDouble(set.getPaymentTerm().label)/360)
                + set.getInvoiceAmount() * (set.getCommissionFee()/100);
        return answer;
    }





    @After
    public void tearDown(){
        driver.close();
    }
}
