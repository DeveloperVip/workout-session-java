package workout;

import java.io.Serializable;
import java.time.LocalDate;

public class Workout implements Serializable {
    private static final long serialVersionUID = 1L; //L là để biên dịch java hiểu được là giá trị long

    private int id;
    private int userId;
    private String activity;
    private int duration;
    private int completedTime;
    private boolean completed;
    private LocalDate date;
    private String notes;

    public Workout(int id, int userId, String activity, int duration, LocalDate date, String notes, int completedTime, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.activity = activity;
        this.duration = duration;
        this.date = date;
        this.notes = notes;
        this.completedTime = completedTime;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getActivity() {
        return activity;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }

    public int getCompletedTime(){
        return completedTime;
    }

    public boolean isCompleted(){
        return completed;
    }

    @Override
    public String toString() {
        return "[" + date + "] " + activity + " - " + duration + " mins" + (notes != null ? ", Note: " + notes : "");
    }
}