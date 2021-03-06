package View;


import View.FullCalendarView;
import Model.Calendar;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.time.YearMonth;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class Dashboard extends Pane implements PlannerListener {
    //Subview classes for the sidebar
    DaySidebar dayView;
    FullCalendarView calendarView;
    GradeSidebar gradeView;
    TaskSidebar taskView;


    // Window set up values
    Rectangle2D bounds;

    // Bounding boxes for the window objects
    BorderPane border;
    VBox calendarBoundingBox; // To control the size of the calendar
    VBox calendarBox;
    VBox sideBar;

    // Parts of the tab pane
    TabPane tabPane;
    Tab grades;
    Tab tasks;
    Tab day;

    // Boxes to add to the tabs
    VBox gradesBox;
    VBox tasksBox;
    VBox dayBox;

    // Buttons for adding new grades, tasks, and day
    Button addGradesb;
    Button addTasksb;
    Button addDayb;

    // The view the calendar is stored in
    FullCalendarView calendarv;

    // Lists for the tabs
    ListView<VBox> gradesList;
    ListView<VBox> tasksList;
    ListView<VBox> dayList;

    public Dashboard(Rectangle2D bounds, FullCalendarView calendarView, GradeSidebar gradeView, TaskSidebar taskView, DaySidebar dayView) {
        this.taskView = taskView;
        this.gradeView = gradeView;
        this.calendarView = calendarView;
        this.dayView = dayView;
        this.bounds = bounds;

        border = new BorderPane();
        tabPane = new TabPane();

        //createTabComponents();
        createTabs();

        // Set the bounds of the calendar
        calendarBoundingBox = new VBox(calendarView.getView());
        calendarBoundingBox.setMaxSize((bounds.getWidth() * 2 / 3) - bounds.getWidth() * 0.05, bounds.getHeight());
        calendarBoundingBox.setAlignment(Pos.CENTER);
        calendarBoundingBox.setStyle("-fx-background-color: lightgrey");

        // set the calender view to the window
        calendarBox = new VBox(calendarBoundingBox);
        calendarBox.setPrefSize(bounds.getWidth() * 2 / 3, bounds.getHeight());
        calendarBox.setAlignment(Pos.CENTER);
        calendarBox.setStyle("-fx-background-color: dimgrey");

        // Set the buttons to the side bar
        //buttonBox = new HBox(tabPane);
        //buttonBox.setPrefSize(bounds.getWidth()/3, 40);
        //buttonBox.setAlignment(Pos.TOP_CENTER);

        // Set the the buttons on the side bar
        sideBar = new VBox(tabPane);
        //sideBar.setPrefSize(bounds.getWidth() / 3, bounds.getHeight());
        sideBar.setAlignment(Pos.TOP_CENTER);
        sideBar.setStyle("-fx-background-color: darkgrey");

        // Set the two regions onto the main window
        border.setLeft(calendarBox);
        border.setRight(sideBar);

        border.setPrefHeight(bounds.getHeight());
        border.setPrefWidth(bounds.getWidth());

        this.setPrefHeight(bounds.getHeight());
        this.setPrefWidth(bounds.getWidth());
        this.getChildren().add(border);
    }


    public void draw() {

    }

    /*
        Create the tabs for the sidebar
     */
    public void createTabs () {

        grades = new Tab("Grades", new Label("Show all the grades available"));
        tasks = new Tab("Tasks", new Label("Show all tasks for the month"));
        day = new Tab("Today's Events", new Label("Show all day events, grades, courses"));

        grades = new Tab("Grades", gradeView);
        tasks = new Tab("Tasks"  , taskView);
        day = new Tab("Today's Events" , dayView);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setTabMinWidth(bounds.getWidth()/10.5);
        tabPane.getTabs().add(day);
        tabPane.getTabs().add(tasks);
        tabPane.getTabs().add(grades);

    }

    public void createTabComponents(){

        //Initialize the component for the grades type

        // Initialize the list
        gradesList = new ListView();
        gradesList.setPrefWidth(100);
        gradesList.setPrefHeight(700);
        gradesList.fixedCellSizeProperty();

        // Initialize the button
        addGradesb = new Button("New Grade");
        addGradesb.setPrefHeight(60);
        addGradesb.setPrefWidth(100);


        // Initialize Components for the tasks tab
        tasksList = new ListView();
        tasksList.setPrefWidth(100);
        tasksList.setPrefHeight(700);
        tasksList.fixedCellSizeProperty();

        addTasksb = new Button("New Task");
        addTasksb.setPrefHeight(60);
        addTasksb.setPrefWidth(100);


        // Initialize Components for the day tab
        // Does List view initialization
        dayList = new ListView();
        dayList.setPrefWidth(100);
        dayList.setPrefHeight(700);
        dayList.fixedCellSizeProperty();

        addDayb = new Button("New Event");
        addDayb.setPrefHeight(60);
        addDayb.setPrefWidth(100);

        // Assign the list views and the buttons to their appropriate
        gradesBox = new VBox(gradesList, addGradesb);
        gradesBox.setPrefSize(100, 800);
        gradesBox.setAlignment(Pos.CENTER_LEFT);

        tasksBox = new VBox(tasksList, addTasksb);
        tasksBox.setPrefSize(100, 800);
        tasksBox.setAlignment(Pos.CENTER_LEFT);

        dayBox = new VBox(dayList, addDayb);
        dayBox.setPrefSize(100, 800);
        dayBox.setAlignment(Pos.CENTER_LEFT);
    }

    public VBox getCalendarBox(){

        return calendarBox;
    }

    public VBox getSideBar(){

        return sideBar;
    }

    public void modelChanged() {
        draw();
    }
}
