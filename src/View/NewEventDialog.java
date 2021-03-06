package View;

import Model.Event;
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


public class NewEventDialog extends InputDialog {

    private final ButtonType doneButtonType;
    VBox addEventBox;
    TextField title;
    TextField desc;
    ComboBox<String> courseChoice, colourChoice;
    ToggleGroup startAMPM, endAMPM;
    DatePicker datePicker;
    Spinner<Integer> startHour, startMinute, endHour, endMinute;
    TextField location;

    public NewEventDialog(ArrayList<String> courseStrings) {
        super();
        this.setTitle("Create an Event");

        doneButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(doneButtonType, ButtonType.CANCEL);

        addEventBox = new VBox();
        addEventBox.setPrefWidth(400);

        Label mainLabel = new Label("Enter new event information");
        mainLabel.setFont(new Font("Arial", 16));
        title = new TextField();
        title.setPromptText("Title");
        Tooltip.install(title, new Tooltip("Hitting done with an empty title is the same as hitting cancel"));
        desc = new TextField();
        desc.setPromptText("Description");

        ObservableList<String> courses = FXCollections.observableArrayList(courseStrings);
        courseChoice = new ComboBox<>(courses);
        courseChoice.setValue("None");

        ObservableList<String> colours = FXCollections.observableArrayList("Green", "Blue", "Red",
                "Orange", "Yellow");
        colourChoice = new ComboBox<>(colours);
        colourChoice.setValue("Green");

        datePicker = new DatePicker(LocalDate.now());
        datePicker.setEditable(false);

        startAMPM = new ToggleGroup();
        endAMPM = new ToggleGroup();
        startHour = new Spinner<>(1, 12, 1, 1);
        startMinute = new Spinner<>(0, 59, 0);
        endHour = new Spinner<>(1, 12, 2, 1);
        endMinute = new Spinner<>(0, 59, 0, 1);
        HBox startTimeBox = createTimeBox(startHour, startMinute, startAMPM);
        HBox endTimeBox = createTimeBox(endHour, endMinute, endAMPM);
        Tooltip.install(endHour, new Tooltip("End time must be after start time. Will otherwise default to being " +
                "equal to start time."));
        Tooltip.install(endMinute, new Tooltip("End time must be after start time. Will otherwise default to " +
                "being equal to start time."));
        location = new TextField();
        location.setPromptText("Location");

        addEventBox.getChildren().addAll(mainLabel, new Label("(Must enter all starred fields)"),
                new Label("Title (*):"), title, new Label("Description:"), desc,
                new Label("Course:"), courseChoice, new Label("Colour:"), colourChoice,
                new Label("Date: (*)"), datePicker, new Label("Start time:"), startTimeBox,
                new Label("End time:"), endTimeBox, new Label("Location:"), location);

        this.getDialogPane().setContent(addEventBox);

        Platform.runLater(() -> title.requestFocus());

        // Convert the result to an event when the done button is clicked.
        this.setResultConverter(dialogButton -> createEventOnDoneClicked(dialogButton));
    }

    private Event createEventOnDoneClicked(Object dialogButton) {
        if (dialogButton == doneButtonType) {
            Color c;
            try {
                String input = colourChoice.getValue();
                // Field field = Class.forName("java.awt.Color").getField(colourChoice.getValue());
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
                c = Color.GREEN; // Not defined
            }

            if(title.getText().isEmpty()){
                return null;
            }

            int mod = 0;
            if(((RadioButton)startAMPM.getSelectedToggle()).getText().equals("PM")){
                System.out.println("Here");
                mod = 12;
            }

            LocalTime sTime;
            if(startHour.getValue() == 12){
                sTime = LocalTime.of(startHour.getValue(), startMinute.getValue());
            }
            else {
                sTime = LocalTime.of(startHour.getValue() + mod, startMinute.getValue());
            }

            mod = 0;
            if (((RadioButton)endAMPM.getSelectedToggle()).getText().equals("PM")){
                mod = 12;
            }
            LocalTime eTime;

            if(endHour.getValue() == 12){
                eTime = LocalTime.of(endHour.getValue(), endMinute.getValue());
            }
            else{
                eTime = LocalTime.of(endHour.getValue()+mod, endMinute.getValue());
            }

            if(((RadioButton)startAMPM.getSelectedToggle()).getText().equals("AM") && (endHour.getValue()) == 12){
                eTime = LocalTime.of(0, endMinute.getValue());
            }
            if(((RadioButton)startAMPM.getSelectedToggle()).getText().equals("AM") && (startHour.getValue()) == 12){
                sTime = LocalTime.of(0, startMinute.getValue());
            }

            //need at least a minute in between
            if(sTime.isAfter(eTime)) {
                return null;
            }

            Event newEvent =  new Event(title.getText(), desc.getText(), courseChoice.getValue(), c,
                    datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(),
                    datePicker.getValue().getYear(), Time.valueOf(sTime), Time.valueOf(eTime), location.getText());

            if (!courseChoice.getValue().equals("None")){
                newEvent.setCourseName(courseChoice.getValue());
            } else {
                newEvent.setCourseName("Default");
            }

            return newEvent;
        }
        return null;
    }
}
