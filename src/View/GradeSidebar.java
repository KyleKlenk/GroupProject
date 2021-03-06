package View;

import Controller.GradeTabController;
import Model.Assessment;
import Model.Course;
import Model.CoursesModel;
import Model.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class GradeSidebar extends VBox implements PlannerListener {

    private CoursesModel model;
    ListView gradesList;
    VBox summary = new VBox();
    ObservableList<HBox> assessmentsListArray;
    private Button addGradeButton, addCourseButton, removeCourseButton;
    ObservableList<String> courses;
    ComboBox<String> courseChoice;
    GradeTabController controller;

    Stage primaryStage;

    public GradeSidebar(Rectangle2D bounds, CoursesModel mdl) {
        //Initialize the component for the grades type
        super();
        model = mdl;
        //Initialize and fill list of courses
        courseChoice = new ComboBox<>();
        courseChoice.setValue("None");
        populateCoursesList();
        courseChoice.setPrefWidth(700);

        // Initialize the list
        gradesList = new ListView();
        gradesList.setPrefWidth(100);
        gradesList.setPrefHeight(705);
        gradesList.fixedCellSizeProperty();

        //Initialize the course button
        addCourseButton = new Button("New Course");
        addCourseButton.setPrefHeight(60);
        addCourseButton.setPrefWidth(100);

        // Initialize the grade button
        addGradeButton = new Button("New Grade");
        addGradeButton.setPrefHeight(60);
        addGradeButton.setPrefWidth(100);

        // Initialize the remove grade button
        removeCourseButton = new Button("Remove Course");
        removeCourseButton.setPrefWidth(140);

        // Title fields
        Label title = new Label("Title");
        Label grade = new Label("Grade");
        Label weight = new Label("Weight");

        HBox fields = new HBox();
        fields.setPadding(new Insets(2,2,2,2));

        VBox titleBox = new VBox(title);
        titleBox.setPrefSize(100, 50);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setPadding(new Insets(0, 0, 0, 8));

        VBox gradeBox = new VBox(grade);
        gradeBox.setPrefSize(100, 50);
        gradeBox.setAlignment(Pos.CENTER_LEFT);
        gradeBox.setPadding(new Insets(0, 0, 0, 8));

        VBox weightBox = new VBox(weight);
        weightBox.setPrefSize(100, 50);
        weightBox.setAlignment(Pos.CENTER_LEFT);
        weightBox.setPadding(new Insets(0, 0, 0, 8));

        fields.getChildren().addAll(titleBox, gradeBox, weightBox);
        fields.setPrefHeight(100);

        // ButtonBar
        HBox buttonBar = new HBox(addGradeButton, addCourseButton, removeCourseButton);
        buttonBar.setPrefHeight(100);

        generateSummary();

        this.setPadding(new Insets(2, 5, 5, 2));
        this.setPrefSize(100, bounds.getHeight());
        //this.setPrefSize(100,800);
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().addAll(courseChoice, fields, gradesList, summary, buttonBar);

        // Draw will draw the sidebar with all the grades
        draw();
    }

    public void draw() {
        generateGradesList();
        populateCoursesList();
        generateSummary();
    }

    public void modelChanged() {
        draw();
    }

    public void setGradeController(GradeTabController controller) {
        this.controller = controller;
    }

    public void setButtonController(GradeTabController controller) {
        addGradeButton.setOnAction(controller::handleAddGradeClicked);
        addCourseButton.setOnAction(controller::handleAddCourseClicked);
        removeCourseButton.setOnAction(controller::handleRemoveCourseClicked);
        courseChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (oldValue != null && newValue != null && !oldValue.equals(newValue)) {
                    model.setSelectedCourse(newValue);
                    modelChanged();
                }
            }
        });
    }

    private void populateCoursesList() {
        String currentChoice;
        ArrayList<String> courseStrings = new ArrayList<>();
        ArrayList<Course> allCourses = model.getCourseList();
        courseStrings.add("None");
        for (Course c : allCourses) {
            courseStrings.add(c.getTitle());
        }
        courses = FXCollections.observableArrayList(courseStrings);
        if (courseChoice != null) {
            currentChoice = courseChoice.getValue();
            courseChoice.getItems().clear();
            courseChoice.getItems().addAll(courses);
            courseChoice.setValue(currentChoice);
        }
    }

    private void generateSummary() {
        Label summaryTitle = new Label("Summary");
        summaryTitle.setFont(new Font("Ariel",20));
        //summaryTitle.setStyle("-fx-font-weight: bold");
        summaryTitle.setUnderline(true);
        Label averageGrade = new Label("Average Grade = " + model.getAverageGrade());
        Label minimumGrade = new Label("Minimum Grade = " + model.getMinimumGrade());
        //summary.setBackground(Background.EMPTY);
        summary.setStyle("-fx-background-color: rgba(255, 230, 204, 1);");
        summary.setPadding(new Insets(5, 5, 5, 5));
        summary.getChildren().setAll(summaryTitle, averageGrade, minimumGrade);
        summary.setPrefHeight(120);
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void generateGradesList() {
        ArrayList<Assessment> assessments = model.getAssessmentList();
        if (assessments == null) {
            return;
        }
        assessmentsListArray = FXCollections.observableArrayList();

        int i = 0;
        for (Assessment currentAssessment : assessments) {

            Label title = new Label(currentAssessment.getTitle());
            Label value = new Label(currentAssessment.getMark() + "%");
            Label weight = new Label(currentAssessment.getWeight() + "");

            Button detailsButton = new Button("Details");
            detailsButton.setPrefSize(70, 40);
            initializeDetailsButton(currentAssessment, detailsButton);

            Button removeButton = new Button("Remove");
            removeButton.setPrefSize(70, 40);
            initializeRemoveButton(currentAssessment, removeButton);

            HBox box = new HBox();
            box.setPadding(new Insets(2,2,2,2));

            HBox titleBox = new HBox(title);
            titleBox.setAlignment(Pos.CENTER_LEFT);
            titleBox.setPrefSize(91, 50);

            HBox markBox = new HBox(value);
            markBox.setAlignment(Pos.CENTER_LEFT);
            markBox.setPadding(new Insets(0, 0, 0, 8));
            markBox.setPrefSize(100, 50);

            HBox weightBox = new HBox(weight);
            weightBox.setAlignment(Pos.CENTER_LEFT);
            weightBox.setPadding(new Insets(0, 0, 0, 8));
            weightBox.setPrefSize(60, 50);

            HBox detailsButtonBox = new HBox(detailsButton);
            detailsButtonBox.setPrefSize(80, 50);
            detailsButtonBox.setAlignment(Pos.CENTER_LEFT);

            HBox removeButtonBox = new HBox(removeButton);
            removeButtonBox.setPrefSize(75, 50);
            removeButtonBox.setAlignment(Pos.CENTER_LEFT);

            box.getChildren().addAll(titleBox, markBox, weightBox, detailsButtonBox, removeButtonBox);
            box.setAlignment(Pos.CENTER_LEFT);
            box.setPrefSize(400, 50);

            // Generates alternating colours for the boxes
            if (i % 2 == 0) {
                box.setStyle("-fx-background-color: lavender");
                //gradeDisplayInfo.setStyle("-fx-background-color: lavender");
            } else {
                box.setStyle("-fx-background-color: lightslategray");
                //gradeDisplayInfo.setStyle("-fx-background-color: lightslategrey");
            }

            assessmentsListArray.add(box);
            i++;
        }
        gradesList.setItems(assessmentsListArray);
    }

    private void initializeDetailsButton(Assessment currentAssessment, Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                dialog.setTitle(currentAssessment.getTitle() + " Details");
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
                Label weight = new Label("Weight: " + currentAssessment.getWeight());
                weight.setFont(new Font("Ariel", 15));

                Label mark = new Label("Mark: "+ currentAssessment.getMark() + "%");
                mark.setFont(new Font("Ariel", 15));

                Label title = new Label(currentAssessment.getTitle());
                title.setFont(new Font("Ariel", 16));

                Label course = new Label(currentAssessment.getCourseTitle());
                course.setFont(new Font("Ariel", 15));

                Label description = new Label(currentAssessment.getDescription());
                description.setFont(new Font("Ariel", 15));
                description.setWrapText(true);

                top.getChildren().addAll(title, course, weight, mark);
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

    public void initializeRemoveButton(Assessment currentAssessment, Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.setAssessmentToDelete(currentAssessment);
                controller.handleRemoveAssessmentClicked(actionEvent);
            }
        });
    }
}
