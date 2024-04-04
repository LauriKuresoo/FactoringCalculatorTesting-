import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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

        assertEquals( correctOutput, applicationOutput);
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
        closeCookies();
        WebElement invoiceField = driver.findElement(By.xpath("//ui-field[@data-wt-label='Invoice amount']"));
        assertEquals("EUR", invoiceField.findElement(By.className("units")).getText());

        WebElement advanceRate = driver.findElement(By.xpath("//ui-field[@data-wt-label='Advance rate']"));
        assertEquals("%", advanceRate.findElement(By.className("units")).getText());

        WebElement interestRate = driver.findElement(By.xpath("//ui-field[@data-wt-label='Interest rate']"));
        assertEquals("%", interestRate.findElement(By.className("units")).getText());

        WebElement paymentTerm = driver.findElement(By.xpath("//ui-field[@data-wt-label='Payment term']"));
        assertEquals("days", paymentTerm.findElement(By.className("units")).getText());

        WebElement commissionFee = driver.findElement(By.xpath("//ui-field[@data-wt-label='Commission fee']"));
        assertEquals("% per invoice", commissionFee.findElement(By.className("units")).getText());
    }
    
    @Test
    public void tryAllSelectorValues(){
        closeCookies();
        TestSet set = new TestSet(2.0, AdvanceRate.a90, 2.0, PaymentTerm.a60, 2.0 );
        for (AdvanceRate rate : AdvanceRate.values()) {
            for (PaymentTerm term : PaymentTerm.values()) {
                set.setAdvanceRate(rate);
                set.setPaymentTerm(term);
                fillInputFields(set);
                calculate();

                double correctOutput = calculatateValidOutput(set);
                WebElement result = driver.findElement(By.id("result"));
                double applicationOutput = Double.valueOf(result.getText());

                assertEquals( correctOutput, applicationOutput);
            }
        }
    }

    @Test
    public void fincaningExpensePercentageTest(){
        closeCookies();
        TestSet set = new TestSet(100.0, AdvanceRate.a90, 5.3, PaymentTerm.a60, 2.7);
        fillInputFields(set);
        calculate();

        double correctOutput = calculatateValidOutput(set);
        WebElement result = driver.findElement(By.id("result_perc"));
        double applicationOutput = Double.valueOf(result.getText());

        BigDecimal relation = new BigDecimal((correctOutput/set.getInvoiceAmount())*100).setScale(2, RoundingMode.HALF_UP);

        assertEquals( relation.doubleValue(), applicationOutput);
    }

}
