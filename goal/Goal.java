package goal;

import java.io.Serializable;
import java.time.LocalDate;

public class Goal implements Serializable {
    private static final long serialVersionUID = 1L;

    private int userId;
    private String description;
    private LocalDate targetDate;

    public Goal(int userId, String description, LocalDate targetDate) {
        this.userId = userId;
        this.description = description;
        this.targetDate = targetDate;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "userId=" + userId +
                ", description='" + description + '\'' +
                ", targetDate=" + targetDate +
                '}';
    }
}
