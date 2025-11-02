import java.util.*;

public class BankApp {
    private static final Scanner in = new Scanner(System.in);
    private static final Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("=== Mini Bank System ===");
        while (true) {
            menu();
            String choice = in.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> createAccount();
                    case "2" -> deposit();
                    case "3" -> withdraw();
                    case "4" -> checkBalance();
                    case "5" -> showHistory();
                    case "6" -> listAccounts();
                    case "0" -> { System.out.println("Goodbye!"); return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void menu() {
        System.out.println("""
            1) Create account
            2) Deposit
            3) Withdraw
            4) Check balance
            5) Transaction history
            6) List accounts
            0) Exit
            Enter choice: """);
    }

    private static void createAccount() {
        System.out.print("Enter account ID: ");
        String id = in.nextLine().trim();
        if (id.isEmpty()) { System.out.println("ID cannot be empty."); return; }
        if (accounts.containsKey(id)) { System.out.println("ID already exists."); return; }

        System.out.print("Enter account holder name: ");
        String name = in.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }

        accounts.put(id, new Account(id, name));
        System.out.println("Account created.");
    }

    private static void deposit() {
        Account acc = findAccount();
        if (acc == null) return;
        double amt = promptAmount("Enter amount to deposit: ");
        acc.deposit(amt);
        System.out.printf("Deposited %.2f. Balance: %.2f%n, ", amt, acc.getBalance());
    }

    private static void withdraw() {
        Account acc = findAccount();
        if (acc == null) return;
        double amt = promptAmount("Enter amount to withdraw: ");
        acc.withdraw(amt);
        System.out.printf("Withdrew %.2f. Balance: %.2f%n", amt, acc.getBalance());
    }

    private static void checkBalance() {
        Account acc = findAccount();
        if (acc == null) return;
        System.out.printf("Account %s | Holder: %s | Balance: %.2f%n",
                acc.getId(), acc.getHolder(), acc.getBalance());
    }

    private static void showHistory() {
        Account acc = findAccount();
        if (acc == null) return;
        System.out.println("Transaction history:");
        for (String h : acc.getHistory()) System.out.println(" - " + h);
    }

    private static void listAccounts() {
        if (accounts.isEmpty()) { System.out.println("No accounts yet."); return; }
        System.out.println("Accounts:");
        accounts.values().forEach(a ->
                System.out.printf(" - ID: %s | Holder: %s | Balance: %.2f%n",
                        a.getId(), a.getHolder(), a.getBalance()));
    }

    private static Account findAccount() {
        System.out.print("Enter account ID: ");
        String id = in.nextLine().trim();
        Account acc = accounts.get(id);
        if (acc == null) System.out.println("Account not found.");
        return acc;
    }

    private static double promptAmount(String prompt) {
        System.out.print(prompt);
        String s = in.nextLine().trim();
        double val;
        try {
            val = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Enter a valid number.");
        }
        if (val <= 0) throw new IllegalArgumentException("Amount must be > 0");
        return val;
    }
}
