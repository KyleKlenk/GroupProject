package View;

import Model.Course;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class NewCourseDialog extends InputDialog {
    ButtonType doneButtonType;
    VBox addCourseBox;
    TextField title, prof, desc;

    public NewCourseDialog() {
        this.setTitle("Create A Course");

        doneButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);

        addCourseBox = new VBox();
        addCourseBox.setPrefWidth(400);

        Label mainLabel = new Label("Enter new course information");
        mainLabel.setFont(new Font("Arial", 16));
        title = new TextField();
        title.setPromptText("Title");
        Tooltip.install(title, new Tooltip("Hitting done with an empty title is the same as hitting cancel"));
        prof = new TextField();
        prof.setPromptText("Instructor");
        desc = new TextField();
        desc.setPromptText("Description");


        addCourseBox.getChildren().addAll(mainLabel, new Label("(Must enter all starred fields)"),
                new Label("Title (*):"), title, new Label("Instructor:"), prof,
                new Label("Description:"), desc);

        this.getDialogPane().setContent(addCourseBox);

        Platform.runLater(() -> title.requestFocus());

        // Convert the result to an assessment when the done button is clicked.
        this.setResultConverter(dialogButton -> createCourseOnDoneClicked(dialogButton));
    }

    private Course createCourseOnDoneClicked(Object dialogButton){
        if (dialogButton == doneButtonType) {
            System.out.println("Created assessment");
            if(title.getText().isEmpty()){
                return null;
            }

            Course newCourse = new Course(title.getText(), prof.getText(), desc.getText());

            return newCourse;
        }
        return null;
    }
}
