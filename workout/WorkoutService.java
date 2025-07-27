package workout;

import account.Account;
import goal.Goal;
import java.time.temporal.ChronoUnit;
import goal.GoalService;
import java.time.LocalDate;
import java.util.List;
import java.util.*;
import java.util.Set;
import java.util.stream.Collectors;

public class WorkoutService {
    private final WorkoutInterface workoutInterface = new WorkoutImpl();
    private static final GoalService goalService = new GoalService();
    private final Scanner scanner = new Scanner(System.in);
    private static int idCounter = 1;

    public WorkoutService() {
        List<Workout> allWorkouts = workoutInterface.getAllWorkouts();
        int maxId = allWorkouts.stream()
                .mapToInt(Workout::getId)
                .max()
                .orElse(0);
        idCounter = maxId + 1;
    }

    public void createSession(Account user) {
        System.out.println("Enter workout name: ");
        String activity = scanner.nextLine();
        int duration = -1;
        do {
            System.out.print("Enter duration in minutes: ");
            try {
                duration = Integer.parseInt(scanner.nextLine());
                if (duration <= 0) {
                    System.out.println("Duration must be a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                duration = -1;
            }
        } while (duration <= 0);    
        LocalDate today = LocalDate.now();

        Workout workout = new Workout(idCounter++, user.getId(), activity, duration, today, null, 0, false);
        workoutInterface.saveWorkout(workout);
        System.out.println("Workout created!");
    }

    public void viewSchedule(Account user) {
        List<Workout> list = workoutInterface.getWorkoutsByUser(user.getId());
        System.out.println("\n===== Your Schedule =====");
        if (list.isEmpty()) {
            System.out.println("No workouts found.");
        } else {
                for (Workout w : list) {
                System.out.println("ID: " + w.getId());
                System.out.println("Name Activity: " + w.getActivity());
                System.out.println("Date: " + w.getDate());
                System.out.println("Duration: " + w.getDuration() + " minutes");
                System.out.println("Notes: " + (w.getNotes() == null ? "No notes" : w.getNotes()));
                System.out.println("----------------------");
            } 
        }
    }

    public void recordResult(Account user) {
        List<Workout> list = workoutInterface.getWorkoutsByUser(user.getId());
        viewSchedule(user);
        if (list.isEmpty()) return;                                                          
        System.out.print("Enter workout ID to record result: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter notes: ");
        String notes = scanner.nextLine();
        System.out.print("Enter completed time: ");
        int completedTime = Integer.parseInt(scanner.nextLine());

        workoutInterface.recordWorkoutResult(user.getId(), id, notes, completedTime, false);
        System.out.println("Result recorded.");
    }

    public void showStatistics(Account user) {
        List<Workout> list = workoutInterface.getWorkoutsByUser(user.getId());
        
        if (list.isEmpty()) {
            System.out.println("You have no workout records.");
            return;
        }

        System.out.println("=== Workout Records ===");
        for (Workout w : list) {
            System.out.println("ID: " + w.getId());
            System.out.println("Date: " + w.getDate());
            System.out.println("Duration: " + w.getDuration() + " minutes");
            System.out.println("Completed time: " + w.getCompletedTime() + " minutes");
            System.out.println("Status completed: " + w.isCompleted());
            System.out.println("Notes: " + (w.getNotes() == null ? "No notes" : w.getNotes()));
            System.out.println("----------------------");
        }

        int totalDuration = list.stream().mapToInt(Workout::getCompletedTime).sum();

        System.out.println("\n=== Statistics Summary ===");
        System.out.println("Total sessions: " + list.size());
        System.out.println("Total workout time: " + totalDuration + " minutes");
        
        //Lọc ra các buổi tập luyện đã hoàn thành từ danh sách list các Workout
        List<Workout> completed = list.stream()
        .filter(Workout::isCompleted)
        .collect(Collectors.toList());

        //Lấy ngày của mỗi buổi tập đã hoàn thành, lưu vào một Set<LocalDate> để loại trùng
        Set<LocalDate> daysTrained = completed.stream()
            .map(Workout::getDate)
            .collect(Collectors.toSet());

        int trainedDays = daysTrained.size();

        List<Goal> goals = goalService.getGoalsByUserId(user);
        if (!goals.isEmpty()) {
            for (Goal goal : goals) {
                LocalDate targetDate = goalService.getTargetDate(goal); 

                //Tìm ngày sớm nhất mà người dùng đã tập luyện
                Optional<LocalDate> earliestDateOpt = daysTrained.stream().min(LocalDate::compareTo);
                if (earliestDateOpt.isEmpty()) {
                    System.out.println("No any session !!!!!");
                    continue;
                }

                LocalDate startDate = earliestDateOpt.get();

                //số ngày từ startDate đến targetDate
                int targetDays = (int) ChronoUnit.DAYS.between(startDate, targetDate) + 1;
                targetDays = Math.max(targetDays, 1);

                double progress = trainedDays * 100.0 / targetDays;

                System.out.println("\n--- Goal ---");
                System.out.println("Start date: " + startDate);
                System.out.println("Target date: " + targetDate);
                System.out.println("Target days: " + targetDays);
                System.out.printf("Progress: %.2f%%\n", progress);
                System.out.println(progress >= 100 ? "Goal achieved!" : "Keep going!!!!!");                       
            }
        } else {
            System.out.println("No goal set.");
        }
    }
}