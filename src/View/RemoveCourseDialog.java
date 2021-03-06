package View;

import Model.Course;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;


public class RemoveCourseDialog extends InputDialog {
    ButtonType doneButtonType;
    VBox removeCourseBox;
    TextField title;
    ComboBox<String> courseChoice;

    public RemoveCourseDialog(ArrayList<String> courseStrings) {
        this.setTitle("Remove A Course");

        doneButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);

        removeCourseBox = new VBox();
        removeCourseBox.setPrefWidth(400);

        Label mainLabel = new Label("Select The Course You Would Like To Delete \n" +
                "WARNING: THIS WILL DELETE EVERYTHING \n" +
                "ASSOCIATED WITH THIS COURSE");
        mainLabel.setFont(new Font("Arial", 16));
        ObservableList<String> courses = FXCollections.observableArrayList(courseStrings);
        courseChoice = new ComboBox<>(courses);
        courseChoice.setValue("None");

        removeCourseBox.getChildren().addAll(mainLabel, new Label("Courses: "), courseChoice);
        this.getDialogPane().setContent(removeCourseBox);
//        Platform.runLater(() -> title.requestFocus());
        this.setResultConverter(dialogButton -> createCourseOnDoneClicked(dialogButton));
    }

    private Course createCourseOnDoneClicked(Object dialogButton) {
        if (dialogButton == doneButtonType) {
            Course newCourse = new Course(courseChoice.getValue(), null, null);
            return newCourse;
        }
        return null;
    }
}
