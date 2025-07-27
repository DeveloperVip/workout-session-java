package account;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountImpl implements AccountInterface {
    private static final String FILE_NAME = "accounts.txt";

    @SuppressWarnings("unchecked")
    private List<Account> loadAccounts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Account>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error loading accounts: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveAccounts(List<Account> accounts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }

    @Override
    public Account findByUsername(String username) {
        for (Account acc : loadAccounts()) {
            if (acc.getUsername().equalsIgnoreCase(username)) {
                return acc;
            }
        }
        return null;
    }

    @Override
    public Account findById(int id) {
        for (Account acc : loadAccounts()) {
            if (acc.getId() == id) {
                return acc;
            }
        }
        return null;
    }

    @Override
    public Account create(Account account) {
        List<Account> accounts = loadAccounts();

        // Tự tạo ID tăng dần
        int maxId = accounts.stream().mapToInt(Account::getId).max().orElse(0);
        account.setId(maxId + 1);

        accounts.add(account);
        saveAccounts(accounts);
        return account;
    }

    @Override
    public boolean update(Account account) {
        List<Account> accounts = loadAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId() == account.getId()) {
                accounts.set(i, account);
                saveAccounts(accounts);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        List<Account> accounts = loadAccounts();
        boolean removed = accounts.removeIf(acc -> acc.getId() == id);
        if (removed) saveAccounts(accounts);
        return removed;
    }
}