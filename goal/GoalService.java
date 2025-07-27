package goal;

import account.Account;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GoalService {
    private final GoalInterface goalInterface = new GoalImpl();
    private final Scanner scanner = new Scanner(System.in);

    public void setGoal(Account account) {
        System.out.println("\n===== SET FITNESS GOAL =====");
        System.out.print("Enter your fitness goal description: ");
        String desc = scanner.nextLine();
        System.out.print("Enter target date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();

        try {
            LocalDate targetDate = LocalDate.parse(dateInput);
            Goal goal = new Goal(account.getId(), desc, targetDate);
            goalInterface.addGoal(goal);
            System.out.println("Goal saved successfully!");
        } catch (Exception e) {
            System.out.println("Invalid date format.");
        }
    }

    public void viewGoals(Account account) {
        List<Goal> goals = goalInterface.getGoalsByUser(account.getId());
        System.out.println("\n===== MY GOALS =====");
        if (goals.isEmpty()) {
            System.out.println("You have no goals yet.");
        } else {
            for (Goal g : goals) {
                System.out.println("- " + g.getDescription() + " (Target: " + g.getTargetDate() + ")");
            }
        }
    }

    public List<Goal> getGoalsByUserId(Account account) {
        return goalInterface.getGoalsByUser(account.getId());
    }

    public static LocalDate getTargetDate(Goal goal) {
        return goal.getTargetDate();
    }

}
