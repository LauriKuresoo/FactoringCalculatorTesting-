import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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

    WebElement invoiceAmount = driver.findElement(By.id("D5"));
    WebElement advanceRate = driver.findElement(By.id("D6"));
    WebElement interestRate = driver.findElement(By.id("D7"));
    WebElement paymentTerm = driver.findElement(By.id("D8"));
    WebElement commisionFee = driver.findElement(By.id("D9"));



    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\UserDrivers\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(baseUrl);
    }

    private void invoiceAmountFill(double invoiceAmount){
        this.invoiceAmount.clear();
        this.invoiceAmount.sendKeys(String.valueOf(invoiceAmount));
    }

    private void advanceRateFill(AdvanceRate rate){
        this.advanceRate.clear();
        this.advanceRate.sendKeys(rate.label);
    }

    private void interestRateFill(double interestRate){
        this.interestRate.clear();
        this.interestRate.sendKeys(Double.toString(interestRate));
    }

    private void paymentTermFill(PaymentTerm term){
        this.paymentTerm.clear();
        this.paymentTerm.sendKeys(term.label);
    }

    private void commissionFeeFill(double commissionFee){
        this.commisionFee.clear();
        this.commisionFee.sendKeys(Double.toString(commissionFee));
    }


    void fillInputFields(double invoiceAmount, AdvanceRate advanceRate, double interestRate, PaymentTerm paymentTerm, double commissionFee){
        invoiceAmountFill(invoiceAmount);
        advanceRateFill(advanceRate);
        interestRateFill(interestRate);
        paymentTermFill(paymentTerm);
        commissionFeeFill(commissionFee);
    }

    double calculatateValidOutput(double invoiceAmount, AdvanceRate advanceRate, double interestRate, PaymentTerm paymentTerm, double commissionFee){
        double answer = invoiceAmount *
                (Double.valueOf(advanceRate.label)/100)
                * (interestRate/100)
                * (Double.valueOf(paymentTerm.label)/360)
                + invoiceAmount * (commissionFee/100);
        return answer;
    }





    @After
    public void tearDown(){
        driver.close();
    }
}
