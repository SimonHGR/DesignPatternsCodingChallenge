package strategy;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class FrequentWithdrawalFeeStrategy implements FeeCalculationStrategy {
  private final int withdrawalPeriod;
  private final int withdrawalCount;
  private final int fee;

  public FrequentWithdrawalFeeStrategy(int withdrawalPeriod, int withdrawalCount, int fee) {
    this.withdrawalPeriod = withdrawalPeriod;
    this.withdrawalCount = withdrawalCount;
    this.fee = fee;
  }

  @Override
  public int calculateFee(int balance, int amount, LocalDate today, List<Transaction> history) {
    // count transactions within cut-off period
    LocalDate cutOffDate = today.minusDays(withdrawalPeriod);
    long txnCount = history.stream()
        .filter(txn -> txn.getDate().isAfter(cutOffDate))
        .count();
    // then report fee based on count of recent transactions
    // remember if this succeeds, this transaction counts but is
    // not yet in the history
    int rv = (txnCount >= withdrawalCount) ? fee : 0;
    return rv;
  }
}
