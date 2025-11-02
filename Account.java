import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String id;
    private final String holder;
    private double balance;
    private final List<String> history = new ArrayList<>();

    public Account(String id, String holder) {
        this.id = id;
        this.holder = holder;
        this.balance = 0.0;
        history.add("Account created for " + holder + " (ID: " + id + ")");
    }

    public String getId() { return id; }
    public String getHolder() { return holder; }
    public double getBalance() { return balance; }
    public List<String> getHistory() { return history; }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");
        balance += amount;
        history.add(String.format("Deposited %.2f | New balance: %.2f", amount, balance));
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds");
        balance -= amount;
        history.add(String.format("Withdrew %.2f | New balance: %.2f", amount, balance));
    }
}
