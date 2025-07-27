package workout;

import java.util.List;

public interface WorkoutInterface {
    void saveWorkout(Workout workout);
    List<Workout> getWorkoutsByUser(int userId);
    void recordWorkoutResult(int userId, int workoutId, String notes, int completedTime, boolean isCompleted);
    List<Workout> getAllWorkouts();
}