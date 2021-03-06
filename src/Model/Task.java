package Model;

import java.awt.*;
import java.sql.Time;

public class Task extends CalendarItem {
    int day;
    int month;
    int year;
    Time dueTime;

    public Task(String title, String description, String courseName, Color color,
                int day, int month, int year, Time dueTime) {
        super(title, description, courseName, color);
        this.day = day;
        this.month = month;
        this.year = year;
        this.dueTime = dueTime;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Time getDueTime() {
        return dueTime;
    }

    public void setDueTime(Time dueTime) {
        this.dueTime = dueTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title=" + title +
                ", courseName=" + getCourseName() +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", dueTime=" + dueTime +
                '}';
    }

    public boolean equalsByField(Task task) {
        return this.getTitle().equals(task.getTitle()) &&
                this.getCourseName().equals(task.getCourseName()) &&
                this.getDay() == task.getDay() &&
                this.getDueTime().equals(task.getDueTime());
    }
}
