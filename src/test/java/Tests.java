import org.junit.Test;
import org.openqa.selenium.By;

import static junit.framework.TestCase.assertEquals;

public class Tests extends TestHelper{


    @Test
    public void allValidInputs(){
        fillInputFields(1, AdvanceRate.a80, 1, PaymentTerm.a30, 1);
        double correctOutput = calculatateValidOutput(1, AdvanceRate.a80, 1, PaymentTerm.a30, 1);
        double applicationOutput = Double.valueOf(driver.findElement(By.id("result")).getText());
        assertEquals(correctOutput, applicationOutput);

    }
}
