import org.junit.Assert;
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
        TestSet  set = new TestSet(1.0, AdvanceRate.a75, 1.0, PaymentTerm.a30, 0.0);
        fillInputFields(set);
        calculate();

        double correctOutput = calculatateValidOutput(set);
        WebElement result = driver.findElement(By.id("result"));
        double applicationOutput = Double.valueOf(result.getText());

        assertEquals( round(correctOutput * 100.0) / 100.0, applicationOutput);
    }

    @Test
    public void InvalidInvoiceAmountTests(){
        closeCookies();

        //Values to be tested for invoice amount
        List<Double> invoiceValuesTest = Arrays.asList(0.0, -1.0);
        TestSet  set = new TestSet(1.0, AdvanceRate.a75, 1.0, PaymentTerm.a30, 0.0);

        WebElement result;
        double correctOutput; double applicationOutput;
        for (Double invoiceValue : invoiceValuesTest) {
            set.setInvoiceAmount(invoiceValue);
            fillInputFields(set);
            WebElement invoiceField = driver.findElement(By.xpath("//ui-field[@data-wt-label='Invoice amount']"));
            WebElement error = invoiceField.findElement(By.xpath("//ui-hint[text()='Value must be greater than or equal 1.']"));
            assertEquals("rangeUnderflow" ,error.getAttribute("error-type"));
            calculate();

            result = driver.findElement(By.id("result"));
            applicationOutput = Double.parseDouble(result.getText());

            assertEquals( 0.00, applicationOutput);
        }
    }

    @Test
    public void emptyInvoiceAmountTest(){
        closeCookies();
        TestSet  set = new TestSet(null, AdvanceRate.a75, 1.0, PaymentTerm.a30, 0.0);
        fillInputFields(set);

        WebElement invoiceField = driver.findElement(By.xpath("//ui-field[@data-wt-label='Invoice amount']"));
        WebElement error = invoiceField.findElement(By.xpath("//ui-hint[text()='Please fill out this field.']"));
        assertEquals("valueMissing" ,error.getAttribute("error-type"));

        calculate();

        WebElement result = driver.findElement(By.id("result"));
        double applicationOutput = Double.parseDouble(result.getText());

        assertEquals( 0.00, applicationOutput);
    }

    @Test
    public void InvalidInterestRateTests(){
        closeCookies();

        //Values to be tested for invoice amount
        List<Double> interestRateTestValues = Arrays.asList(-1.0);
        TestSet  set = new TestSet(1.0, AdvanceRate.a75, 1.0, PaymentTerm.a30, 0.0);

        WebElement result;
        double correctOutput; double applicationOutput;
        for (Double interestRatevalue : interestRateTestValues) {
            set.setInterestRate(interestRatevalue);
            fillInputFields(set);
            WebElement invoiceField = driver.findElement(By.xpath("//ui-field[@data-wt-label='Interest rate']"));
            WebElement error = invoiceField.findElement(By.xpath("//ui-hint[text()='Value must be greater than or equal 0.']"));
            assertEquals("rangeUnderflow" ,error.getAttribute("error-type"));

            calculate();

            result = driver.findElement(By.id("result"));
            applicationOutput = Double.parseDouble(result.getText());

            assertEquals( 0.00, applicationOutput);
        }
    }

    @Test
    public void emptyInterestRateTest(){
        closeCookies();
        TestSet  set = new TestSet(1.0, AdvanceRate.a75, null, PaymentTerm.a30, 0.0);
        fillInputFields(set);

        WebElement invoiceField = driver.findElement(By.xpath("//ui-field[@data-wt-label='Interest rate']"));
        WebElement error = invoiceField.findElement(By.xpath("//ui-hint[text()='Please fill out this field.']"));
        assertEquals("valueMissing" ,error.getAttribute("error-type"));

        calculate();

        WebElement result = driver.findElement(By.id("result"));
        double applicationOutput = Double.parseDouble(result.getText());

        assertEquals( 0.00, applicationOutput);
    }
    @Test
    public void emptyCommissionFeeTest(){
        closeCookies();
        TestSet  set = new TestSet(1.0, AdvanceRate.a75, 1.0, PaymentTerm.a30, null);
        fillInputFields(set);

        WebElement commissionFeefield = driver.findElement(By.xpath("//ui-field[@data-wt-label='Commission fee']"));
        WebElement error = commissionFeefield.findElement(By.xpath("//ui-hint[text()='Please fill out this field.']"));
        assertEquals("valueMissing" ,error.getAttribute("error-type"));

        calculate();

        WebElement result = driver.findElement(By.id("result"));
        double applicationOutput = Double.parseDouble(result.getText());

        assertEquals( 0.00, applicationOutput);
    }

    @Test
    public void inputDataTypeTests(){
        WebElement invoiceField = driver.findElement(By.xpath("//ui-field[@data-wt-label='Commission fee']"));

    }

}
