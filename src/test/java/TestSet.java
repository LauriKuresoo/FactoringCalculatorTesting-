import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestSet {
    Double InvoiceAmount;
    AdvanceRate AdvanceRate;
    Double InterestRate;
    PaymentTerm PaymentTerm;
    Double CommissionFee;

    public TestSet(Double invoiceAmount, AdvanceRate advanceRate, Double interestRate, PaymentTerm paymentTerm, Double commissionFee) {
        InvoiceAmount = invoiceAmount;
        AdvanceRate = advanceRate;
        InterestRate = interestRate;
        PaymentTerm = paymentTerm;
        CommissionFee = commissionFee;
    }
}