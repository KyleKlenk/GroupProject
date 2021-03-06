package Model;

import View.PlannerListener;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TaskBoardModel {
    ArrayList<Task> tasks;
    ArrayList<PlannerListener> subscribers;
    DataBase db;

    public TaskBoardModel() {
        subscribers = new ArrayList<>();
        db = new DataBase();
        tasks = getTasksFromDB();
    }

    public ArrayList<Task> getTasks(){
        return tasks;
    }

    public void updateTasks() {
        tasks = getTasksFromDB();
        notifySubscribers();
    }

    public ArrayList<Task> getTasksFromDB() {
        ResultSet tasksQuery = db.getAllTasks();
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            while(tasksQuery.next()) {
                SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
                String dueTimeString = tasksQuery.getString("dueTime");

                Time dueTime = new Time(format1.parse(dueTimeString).getTime());

                Color taskColor = new Color(tasksQuery.getInt("colorRedInt"),
                                            tasksQuery.getInt("colorGreenInt"),
                                            tasksQuery.getInt("colorBlueInt"));

                Task task = new Task(tasksQuery.getString("taskTitle"),
                        tasksQuery.getString("taskDescription"),
                        tasksQuery.getString("courseName"),
                        taskColor,
                        tasksQuery.getInt("dueDay"),
                        tasksQuery.getInt("dueMonth"),
                        tasksQuery.getInt("dueYear"),
                        dueTime);
                tasks.add(task);
            }
        } catch (SQLException | ParseException e) {
            System.out.println("Problem getting tasks form DB");
            e.printStackTrace();
        }
        db.closeConnection();
        return tasks;
    }

    public void insertTask(Task userInput) {
        db.insertTask(userInput.getTitle(), userInput.getDescription(), userInput.getCourseName(),
                userInput.getColor().getRed(), userInput.getColor().getGreen(), userInput.getColor().getBlue(),
                userInput.getDay(), userInput.getMonth(), userInput.getYear(), userInput.getDueTime().toString());
        tasks = getTasksFromDB();
        db.closeConnection();
        notifySubscribers();
    }

    public void deleteTask(Task userInput) {
        db.deleteTask(userInput.getTitle(), userInput.getDescription(), userInput.getCourseName(),
                userInput.getColor().getRed(), userInput.getColor().getGreen(), userInput.getColor().getBlue(),
                userInput.getDay(), userInput.getMonth(), userInput.getYear(), userInput.getDueTime().toString());
        db.closeConnection();
        notifySubscribers();
    }

    public void addSubscriber(PlannerListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }
}
