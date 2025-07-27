package workout;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class WorkoutImpl implements WorkoutInterface {
    private static final String FILE_PATH = "workouts.txt";
    private List<Workout> workouts;

    public WorkoutImpl() {
        workouts = readFromFile();
    }

    @Override
    public void saveWorkout(Workout workout) {
        workouts.add(workout);
        writeToFile();
    }

    @Override
    public List<Workout> getWorkoutsByUser(int userId) {
        return workouts.stream()
            .filter(w -> w.getUserId() == userId)
            .collect(Collectors.toList());
    }

    @Override
    public void recordWorkoutResult(int userId, int workoutId, String notes, int completedTime, boolean isCompleted) {
        for (Workout w : workouts) {
            if (w.getUserId() == userId && w.getId() == workoutId) {
                if(completedTime > 0){
                    workouts.set(workouts.indexOf(w), new Workout(
                        w.getId(), w.getUserId(), w.getActivity(), w.getDuration(), w.getDate(), notes, completedTime, true
                    ));
                }
                else{
                    workouts.set(workouts.indexOf(w), new Workout(
                        w.getId(), w.getUserId(), w.getActivity(), w.getDuration(), w.getDate(), notes, completedTime, false
                    ));
                }
                break;
            }
        }
        writeToFile();
    }

    private void writeToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(workouts);
        } catch (IOException e) {
            System.err.println("Error writing workouts: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Workout> readFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Workout>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return new ArrayList<>(workouts);
    }
}