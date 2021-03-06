package View;

import Model.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;
import java.lang.reflect.Field;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class NewTaskDialog extends InputDialog {
    private final ButtonType doneButtonType;
    VBox addTaskBox;
    TextField title;
    TextField desc;
    ComboBox<String> courseChoice, colourChoice;
    ToggleGroup groupAMPM;
    DatePicker datePicker;
    Spinner<Integer> hour, minute;

    public NewTaskDialog(ArrayList<String> courseStrings) {
        this.setTitle("Create A Task");

        doneButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);

        addTaskBox = new VBox();
        addTaskBox.setPrefWidth(400);

        Label mainLabel = new Label("Enter new event information");
        mainLabel.setFont(new Font("Arial", 16));
        title = new TextField();
        title.setPromptText("Title");
        Tooltip.install(title, new Tooltip("Hitting done with an empty title is the same as hitting cancel"));
        ObservableList<String> courses = FXCollections.observableArrayList(courseStrings);
        courseChoice = new ComboBox<>(courses);
        courseChoice.setValue("None");
        ObservableList<String> colours = FXCollections.observableArrayList("Green", "Blue", "Red",
                "Orange", "Yellow");
        colourChoice = new ComboBox<>(colours);
        colourChoice.setValue("Green");
        desc = new TextField();
        desc.setPromptText("Description");

        datePicker = new DatePicker(LocalDate.now());
        datePicker.setEditable(false);
        groupAMPM = new ToggleGroup();
        hour = new Spinner<>(1, 12, 2, 1);
        minute = new Spinner<>(0, 59, 0, 1);
        HBox endTimeBox = createTimeBox(hour, minute, groupAMPM);

        addTaskBox.getChildren().addAll(mainLabel, new Label("(Must enter all starred fields)"),
                new Label("Title (*):"), title, new Label("Description:"), desc,
                new Label("Course:"), courseChoice, new Label("Colour:"), colourChoice,
                new Label("Date: (*)"), datePicker, new Label("Time:"), endTimeBox);

        this.getDialogPane().setContent(addTaskBox);

        Platform.runLater(() -> title.requestFocus());

        // Convert the result to an event when the done button is clicked.
        this.setResultConverter(dialogButton -> createTaskOnDoneClicked(dialogButton));
    }

    private Object createTaskOnDoneClicked(Object dialogButton) {
        if (dialogButton == doneButtonType) {
            Color c;
            try {
                String input = colourChoice.getValue();
                // Field field = Class.forName("java.awt.Color").getField(colourChoice.getValue());
                // Reads the value from colour choice and assigns the appropriate value
                if("Green".equals(input)){
                    c = Color.GREEN;
                }
                else if("Blue".equals(input)){
                    c = Color.BLUE;
                }
                else if("Red".equals(input)){
                    c = Color.RED;
                }
                else if("Orange".equals(input)){
                    c = Color.ORANGE;
                }
                else if("Yellow".equals(input)){
                    c = Color.YELLOW;
                }
                else {
                    c = Color.PINK;
                }
            } catch (Exception e) {
                c = Color.GREEN; // Not chosen was not defined
            }

            if(title.getText().isEmpty()){
                return null;
            }

            int mod = 0;
            if (((RadioButton) groupAMPM.getSelectedToggle()).getText().equals("PM") && hour.getValue() != 12){
                mod = 12;
            }

            LocalTime eTime = LocalTime.of(hour.getValue()+mod, minute.getValue());

            if(((RadioButton)groupAMPM.getSelectedToggle()).getText().equals("AM") && (hour.getValue()) == 12){
                eTime = LocalTime.of(0, minute.getValue());
            }


            Task newTask =  new Task(title.getText(), desc.getText(), courseChoice.getValue(), c,
                    datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(),
                    datePicker.getValue().getYear(), Time.valueOf(eTime));

            if (!courseChoice.getValue().equals("None")){
                newTask.setCourseName(courseChoice.getValue());
            } else {
                newTask.setCourseName("Default");
            }

            return newTask;
        }
        return null;
    }
}
