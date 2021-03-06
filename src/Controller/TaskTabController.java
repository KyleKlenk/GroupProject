package Controller;

import Model.CoursesModel;
import Model.Task;
import Model.Course;
import Model.TaskBoardModel;
import View.NewTaskDialog;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Optional;

public class TaskTabController {
    TaskBoardModel model;
    CoursesModel courseModel;
    Task taskToDelete;

    public void setModel(TaskBoardModel newModel) {
        model = newModel;
    }

    public void setCoursesModel(CoursesModel newModel) {
        courseModel = newModel;
    }

    public void setTaskToDelete(Task task) {
        this.taskToDelete = task;
    }

    public void handleAddTaskClicked(ActionEvent actionEvent) {
        ArrayList<String> courseStrings = new ArrayList<>();
        ArrayList<Course> courses = courseModel.getCourseList();
        courseStrings.add("None");
        for(Course c : courses){
            courseStrings.add(c.getTitle());
        }
        Dialog<Task> dialog = new NewTaskDialog(courseStrings);

        Optional<Task> result = dialog.showAndWait();

        result.ifPresent(event -> {


            model.insertTask(event);
        });
    }

    public void handleRemoveTaskClicked(ActionEvent actionEvent) {
        model.deleteTask(taskToDelete);
    }
}
