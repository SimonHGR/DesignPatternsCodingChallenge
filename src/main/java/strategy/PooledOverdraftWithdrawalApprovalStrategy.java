package strategy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PooledOverdraftWithdrawalApprovalStrategy implements WithdrawalApprovalStrategy {
  private static final Map<Integer, WithdrawalApprovalStrategy> pool = new HashMap<>();

  private final int minimumPermittedBalance;

  private  PooledOverdraftWithdrawalApprovalStrategy(int mayBorrow) {
    minimumPermittedBalance = -mayBorrow;
  }

  public static WithdrawalApprovalStrategy borrowing(int mayBorrow) {
    if (mayBorrow % 500 != 0) throw new IllegalArgumentException("Overdraft must be in blocks of 500");
    WithdrawalApprovalStrategy rv = pool.get(mayBorrow);
    if (rv == null) {
      rv = new PooledOverdraftWithdrawalApprovalStrategy(mayBorrow);
      pool.put(mayBorrow, rv);
    }
    return rv;
  }

  @Override
  public boolean grantWithdrawal(int balance, int amount, LocalDate today, List<Transaction> history) {
    // amount here is a *transaction* amount, so expected to be negative
    assert amount < 0 : "withdrawal is a negative value transaction";
    return balance + amount >= minimumPermittedBalance;
  }
}
