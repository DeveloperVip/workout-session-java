package goal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GoalImpl implements GoalInterface {
    private static final String FILE_NAME = "goals.txt";
    private List<Goal> goals;

    public GoalImpl() {
        goals = loadGoalsFromFile();
    }

    @Override
    public void addGoal(Goal goal) {
        goals.add(goal);
        saveGoalsToFile();
    }

    @Override
    public List<Goal> getGoalsByUser(int userId) {
        List<Goal> result = new ArrayList<>();
        for (Goal g : goals) {
            if (g.getUserId() == userId) {
                result.add(g);
            }
        }
        return result;
    }

    private void saveGoalsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(goals);
        } catch (IOException e) {
            System.err.println("Error saving goals: " + e.getMessage());
        }
    }

    //tắt cảnh cáo đối tượng đọc từ file vì Java không thể kiểm tra kiểu generic trong runtime.
    @SuppressWarnings("unchecked")
    private List<Goal> loadGoalsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Goal>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading goals: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
