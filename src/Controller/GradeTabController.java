package Controller;

import Model.Assessment;
import Model.Course;
import Model.CoursesModel;
import View.NewCourseDialog;
import View.NewGradeDialog;
import View.RemoveCourseDialog;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Optional;

public class GradeTabController {
    CoursesModel model;
    Assessment assessmentToDelete;

    public void setModel(CoursesModel newModel) {
        model = newModel;
    }

    public void setAssessmentToDelete(Assessment assessment) {
        this.assessmentToDelete = assessment;
    }

    public void handleAddGradeClicked(ActionEvent actionEvent) {
        ArrayList<String> courseStrings = new ArrayList<>();
        ArrayList<Course> courses = model.getCourseList();
        courseStrings.add("None");
        for(Course c : courses){
            courseStrings.add(c.getTitle());
        }
        Dialog<Assessment> dialog = new NewGradeDialog(courseStrings);
        Optional<Assessment> result = dialog.showAndWait();

        result.ifPresent(event -> {

            model.insertAssessment(event);
        });
    }

    public void handleAddCourseClicked(ActionEvent actionEvent) {
        Dialog<Course> dialog = new NewCourseDialog();

        Optional<Course> result = dialog.showAndWait();

        result.ifPresent(event -> {

            model.insertCourse(event);
        });
    }

    public void handleRemoveCourseClicked(ActionEvent actionEvent) {
        ArrayList<String> courseStrings = new ArrayList<>();
        ArrayList<Course> courses = model.getCourseList();
        for(Course c : courses){
            if(!c.getTitle().equals("Default")){
                courseStrings.add(c.getTitle());
            }
        }
        Dialog<Course> dialog = new RemoveCourseDialog(courseStrings);

        Optional<Course> result = dialog.showAndWait();

        result.ifPresent(event -> {

            model.deleteCourse(event);
        });
    }

    public void handleRemoveAssessmentClicked(ActionEvent actionEvent) {
        model.deleteAssessment(assessmentToDelete);
    }
}
