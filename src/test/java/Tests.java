import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.round;
import static junit.framework.TestCase.*;

public class Tests extends TestHelper{

    @Test
    public void allValidInputs() throws InterruptedException {
        closeCookies();
        TestSet  set = new TestSet(1, AdvanceRate.a75, 1, PaymentTerm.a30, 0);
        fillInputFields(set);
        calculate();

        double correctOutput = calculatateValidOutput(set);
        WebElement result = driver.findElement(By.id("result"));
        double applicationOutput = Double.valueOf(result.getText());

        assertEquals( round(correctOutput * 100.0) / 100.0, applicationOutput);
    }

    @Test
    public void InvoiceAmountTests(){
        closeCookies();
        List<Double> invoiceValues = Arrays.asList(0.0, -1.0, 100.0);
        TestSet  set = new TestSet(1, AdvanceRate.a75, 1, PaymentTerm.a30, 0);

        WebElement result = driver.findElement(By.id("result"));
        double correctOutput; double applicationOutput;
        for (Double invoiceValue : invoiceValues) {
            set.setInvoiceAmount(invoiceValue);
            fillInputFields(set);
            calculate();

            correctOutput = calculatateValidOutput(set);
            applicationOutput = Double.parseDouble(result.getText());

            assertEquals( round(correctOutput * 100.0) / 100.0, applicationOutput);
        }
    }
}
