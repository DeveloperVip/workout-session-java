package goal;

import java.util.List;

public interface GoalInterface {
    void addGoal(Goal goal);
    List<Goal> getGoalsByUser(int userId);
}
