import account.AccountService;
import workout.WorkoutService;
import account.Account;
import goal.GoalService;

import java.util.*;

public class Main{
    private static final Scanner scanner = new Scanner(System.in);
    private static final AccountService accountService = new AccountService();
    private static final WorkoutService workoutService = new WorkoutService();
    private static final GoalService goalService = new GoalService();
    
    private static boolean running = true;
    
    public static void main(String[] args){
        System.out.println("Welcome to Practive Manager!");
        while (running) {
            if (accountService.getCurrentUser() == null) {
                showMainMenu();
            } else {
                showUserMenu();
            }
        }

        scanner.close();
        System.out.println("See you again in next your access. Goodbye!");
    }
    //Menu lựa chọn 
    private static void showMainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        int choice = readIntInput();

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 0:
                running = false;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    private static void showUserMenu() {
        System.out.println("\n===== USER MENU =====");
        System.out.println("1. Create Workout Session");
        System.out.println("2. View My Schedule");
        System.out.println("3. Record Result");
        System.out.println("4. Set Fitness Goal");
        System.out.println("5. View Statistics");
        System.out.println("6. Change Password");
        System.out.println("7. Delete Account");
        System.out.println("8. Logout");
        System.out.println("9. View Goal");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        int choice = readIntInput();

        switch (choice) {
            case 1:
                workoutService.createSession(accountService.getCurrentUser());
                break;
            case 2:
                workoutService.viewSchedule(accountService.getCurrentUser());
                break;
            case 3:
                workoutService.recordResult(accountService.getCurrentUser());
                break;
            case 4:
                goalService.setGoal(accountService.getCurrentUser());
                break;
            case 5:
                workoutService.showStatistics(accountService.getCurrentUser());
                break;
            case 6: 
                changePassword();
                break;
            case 7:
                deleteAccount();
                break;
            case 8:
                accountService.logout();
                System.out.println("Logged out successfully.");
                break;
            case 9:
                goalService.viewGoals(accountService.getCurrentUser());
                break;
            case 0:
                running = false;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    //Các hàm đăng ký đăng nhập 
    private static void register() {
        System.out.println("\n===== REGISTER =====");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();

        Account account = accountService.register(username, password, "USER", fullName, email, phone, address);

        if (account != null) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed. Username may already exist.");
        }
    }
    private static void login() {
        System.out.println("\n===== LOGIN =====");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (accountService.login(username, password)) {
            System.out.println("Login successful!");
            System.out.println("Welcome, " + accountService.getCurrentUser().getFullName() + "!");
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }
    //Validate input 
    private static int readIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    private static void changePassword() {
        System.out.println("\n===== CHANGE PASSWORD =====");

        System.out.print("Current Password: ");
        String currentPassword = scanner.nextLine();

        System.out.print("New Password: ");
        String newPassword = scanner.nextLine();

        System.out.print("Confirm New Password: ");
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("New passwords do not match.");
            return;
        }

        if (accountService.changePassword(accountService.getCurrentUser().getId(), currentPassword, newPassword)) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Failed to change password. Current password might be incorrect.");
        }
    }
    private static void deleteAccount() {
        System.out.println("\n===== DELETE ACCOUNT =====");
        System.out.print("Are you sure you want to delete your account? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            int id = accountService.getCurrentUser().getId();
            if (accountService.deleteAccount(id)) {
                System.out.println("Account deleted successfully.");
                accountService.logout();
            } else {
                System.out.println("Failed to delete account.");
            }
        } else {
            System.out.println("Account deletion cancelled.");
        }
    }
}