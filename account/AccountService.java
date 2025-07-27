package account;

import java.util.List;

public class AccountService {
    private final AccountInterface AccountInterface;
    private Account currentUser;

    public AccountService() {
        this.AccountInterface = new AccountImpl();
    }
    public Account getCurrentUser() {
        return currentUser;
    }

    public boolean login(String username, String password) {
        Account account = AccountInterface.findByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            currentUser = account;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public Account register(String username, String password, String role,
            String fullName, String email, String phone, String address) {
        // Check if username already exists
        if (AccountInterface.findByUsername(username) != null) {
            return null; // Username already taken
        }

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setFullName(fullName);
        account.setEmail(email);
        account.setPhone(phone);
        account.setAddress(address);

        return AccountInterface.create(account);
    }


    public boolean updateAccount(Account account) {
        return AccountInterface.update(account);
    }


    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        Account account = AccountInterface.findById(userId);
        if (account != null && account.getPassword().equals(oldPassword)) {
            account.setPassword(newPassword);
            return AccountInterface.update(account);
        }
        return false;
    }

    public Account createUser(String username, String password, String role,
            String fullName, String email, String phone, String address) {
        return register(username, password, role, fullName, email, phone, address);
    }


    public boolean deleteUser(int userId) {
        return AccountInterface.delete(userId);
    }


    public boolean deleteAccount(int accountId) {
        return deleteUser(accountId);
    }
}