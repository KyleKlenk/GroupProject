package View;

import Controller.DayTabController;
import Model.Calendar;
import Model.Event;
import Model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class DaySidebar extends VBox implements PlannerListener {
    Calendar model;
    VBox dayBox;
    Button addEventbutton;
    ListView dayList;
    ObservableList<HBox> dayListArray;
    Stage primaryStage;
    DayTabController controller;

    public DaySidebar(Rectangle2D bounds) {
        // Initialize Components for the day tab
        // Does List view initialization
        dayList = new ListView<VBox>();
        dayList.setPrefWidth(100);
        dayList.setPrefHeight(700);
        dayList.fixedCellSizeProperty();

        addEventbutton = new Button("New Event");
        addEventbutton.setPrefHeight(60);
        addEventbutton.setPrefWidth(100);

        VBox buttonBar = new VBox(addEventbutton);
        buttonBar.setMaxHeight(65);
        this.setAlignment(Pos.CENTER_LEFT);

        dayBox = new VBox(dayList, addEventbutton);
        dayBox.setPrefSize(100, 800);
        dayBox.setAlignment(Pos.CENTER_LEFT);

        this.setPadding(new Insets(2,5,5,2));
        this.setPrefSize(100, bounds.getHeight());
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(dayBox);
    }

    public void setModel(Calendar newModel) {
        model = newModel;
        draw();
    }

    /*
        On draw, the program will get the applicable events and add them to the sidebar
     */
    public void draw() {
        populateList();
    }

    /*
        When the model changes the view is redrawn
     */
    public void modelChanged() {
        draw();
    }

    /*
        Function responsible for populating the sidebar lists by retrieving the events from the day sidebar
     */
    private void populateList() {
        ArrayList<Event> events = model.getEvents();
        dayListArray = FXCollections.observableArrayList ();

        int i = 0;
        for(Event currentEvent :events){

            Label title = new Label(currentEvent.getTitle());
            Label time = new Label("Time: " + currentEvent.getStart() + " - " + currentEvent.getEnd());
            Label location = new Label("Location: " + currentEvent.getLocation());

            Button detailsButton = new Button("Details");
            detailsButton.setPrefSize(80, 60);

            Button removeButton = new Button("Remove");
            removeButton.setPrefSize(80, 60);

            // Moved details window code to its own function to fully view the event
            initializeDetailsButton(currentEvent, detailsButton);
            initializeRemoveButton(currentEvent, removeButton);

            HBox box = new HBox();
            box.setPadding(new Insets(2,2,2,2));

            VBox left = new VBox(title, time, location);
            left.setPrefSize(250, 60);
            left.setAlignment(Pos.CENTER_LEFT);

            HBox right = new HBox(detailsButton, removeButton);
            right.setPrefSize(300, 60);
            right.setSpacing(20);
            right.setPadding(new Insets(10, 30, 10, 30));
            right.setAlignment(Pos.CENTER_RIGHT);

            box.getChildren().addAll(left, right);
            box.setAlignment(Pos.CENTER_LEFT);
            box.setPrefSize(400, 60);

            if(currentEvent.getColor() == null) {
                if (i % 2 == 0) {
                    box.setStyle("-fx-background-color: lightseagreen");
                } else {
                    box.setStyle("-fx-background-color: lightslategrey");
                }
            }
            else {
                String colour = "-fx-background-color: " + getColour(currentEvent.getColor());

                //System.out.println("Colour found: " + colour);
                box.setStyle(colour);
            }

            dayListArray.add(box);
            i++;
        }

        dayList.setItems(dayListArray);

    }

    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void setController(DayTabController controller){
        this.controller = controller;
    }

    public void setButtonController(DayTabController controller) {
        addEventbutton.setOnAction(controller::handleAddEventClicked);
    }

    /*
        Get the colour of the event and turn it into a string to use for colouring the event box
     */
    public String getColour(Color eventColour){
        String colour = "";

        if (Color.GREEN.equals(eventColour)) {
            colour = "limeGreen";
        }
        else if (Color.BLUE.equals(eventColour)) {
            colour = "skyBlue";
        }
        else if (Color.RED.equals(eventColour)) {
            colour = "crimson";
        }
        else if (Color.ORANGE.equals(eventColour)) {
            colour = "darkOrange";
        }
        else if (Color.YELLOW.equals(eventColour)) {
            colour = "gold";
        }
        else {
            colour = "plum";  //default if not set to any of the of the acceptable colours
        }

        return colour;
    }

    /*
        Initalize functionality for the "Details" button on the event tab
        @param: currentEvent (The event the detaisl are added from), button (the button that will pop up the window)
     */
    public void initializeDetailsButton(Event currentEvent, Button button){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                dialog.setTitle(currentEvent.getTitle() + " Details");

                VBox top = new VBox();
                top.setPrefSize(800, 300);
                top.setPadding(new Insets(5, 5, 5, 5));
                top.setSpacing(2);

                VBox descriptionBox = new VBox();
                descriptionBox.setPrefSize(800, 300);
                descriptionBox.setStyle("-fx-border-color: grey;\n" +
                        "-fx-border-insets: 2;\n" +
                        "-fx-border-width: 2;\n" +
                        "-fx-border-style: solid inside;\n" +
                        "-fx-background-color: lightgrey;\n");
                Label date = new Label("Date: " + currentEvent.getDay() + "/" + currentEvent.getMonth() + "/" + currentEvent.getYear());
                date.setFont(new Font("Arial", 15));

                Label title = new Label(currentEvent.getTitle());
                title.setFont(new Font("Ariel", 16));

                Label course = new Label("Course: " + currentEvent.getCourseName());
                course.setFont(new Font("Arial", 15));

                Label time = new Label("Time: " + currentEvent.getStart() + " - " + currentEvent.getEnd());
                time.setFont(new Font("Ariel", 15));

                Label location = new Label("Location: " + currentEvent.getLocation());
                location.setFont(new Font("Ariel", 15));

                Label description = new Label(currentEvent.getDescription());
                // Allows text to wrap to a second line if neccesary
                description.setWrapText(true);

                top.getChildren().addAll(title, course, date, time, location);
                descriptionBox.getChildren().add(description);

                VBox dialogVbox = new VBox();
                dialogVbox.setPrefSize(800, 800);
                dialogVbox.getChildren().addAll(top, descriptionBox);

                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });
    }

    public void initializeRemoveButton(Event currentEvent, Button button) {
        Event buttonEvent = currentEvent;
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.setEventToDelete(buttonEvent);
                controller.handleRemoveEventClicked(actionEvent);
            }
        });
    }
}
