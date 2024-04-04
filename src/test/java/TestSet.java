import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestSet {
    double InvoiceAmount;
    AdvanceRate AdvanceRate;
    double InterestRate;
    PaymentTerm PaymentTerm;
    double CommissionFee;

    public TestSet(double invoiceAmount, AdvanceRate advanceRate, double interestRate, PaymentTerm paymentTerm, double commissionFee) {
        InvoiceAmount = invoiceAmount;
        AdvanceRate = advanceRate;
        InterestRate = interestRate;
        PaymentTerm = paymentTerm;
        CommissionFee = commissionFee;
    }
}