import Controller.*;
import Model.*;
import View.*;
import View.FullCalendarView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.YearMonth;

public class Main extends Application {
    //Models
    Calendar calendarModel;
    CoursesModel coursesModel;
    TaskBoardModel taskModel;
    //Controllers
    CalendarController calController;
    DayTabController daytabController;
    GradeTabController gradeController;
    TaskTabController taskController;
    //Views
    Dashboard dashboard;
    DaySidebar dayView;
    GradeSidebar gradeView;
    TaskSidebar taskView;
    FullCalendarView calendarView;

    // The view the calendar is stored in
    BorderPane border;

    public static DataBase db;

    @Override
    public void start (Stage primaryStage){
        StackPane root = new StackPane();
        border = new BorderPane();


        calController = new CalendarController();
        daytabController = new DayTabController();
        gradeController = new GradeTabController();
        taskController = new TaskTabController();

        calendarModel = new Calendar();
        taskModel = new TaskBoardModel();
        coursesModel = new CoursesModel(calendarModel, taskModel);


        //Set up the controllers with respective models
        calController.setModel(calendarModel);
        daytabController.setModel(calendarModel);
        daytabController.setCoursesModel(coursesModel);
        gradeController.setModel(coursesModel);
        taskController.setModel(taskModel);
        taskController.setCoursesModel(coursesModel);

        Screen screen = Screen.getPrimary();
        Rectangle2D wBounds = screen.getVisualBounds();
        Rectangle2D bounds = new Rectangle2D(wBounds.getMinX(), wBounds.getMinY(), wBounds.getWidth(),
                wBounds.getHeight()-20);

        dayView = new DaySidebar(bounds);
        taskView = new TaskSidebar(bounds);

        calendarView = new FullCalendarView(YearMonth.now(), calController);
        gradeView = new GradeSidebar(bounds, coursesModel);

        dashboard = new Dashboard(bounds, calendarView, gradeView, taskView, dayView);

        //Set up each view with the model it will draw
        dayView.setController(daytabController);
        taskView.setController(taskController);
        gradeView.setGradeController(gradeController);


        dayView.setModel(calendarModel);
        taskView.setModel(taskModel);
        calendarView.setModel(calendarModel);

        // Populate calendar with the appropriate day numbers
        calendarView.populateCalendar(YearMonth.now());

        dayView.setStage(primaryStage);
        taskView.setStage(primaryStage);
        gradeView.setStage(primaryStage);

        //Set up model-view subscriber relationship
        calendarModel.addSubscriber(dayView);
        calendarModel.addSubscriber(calendarView);
        taskModel.addSubscriber(taskView);
        coursesModel.addSubscriber(gradeView);

        //Establish controller - add-button relationship
        dayView.setButtonController(daytabController);
        gradeView.setButtonController(gradeController);
        taskView.setButtonController(taskController);

        // needed for remove

        // Set the title
        primaryStage.setTitle("CMPT370 Project");


        // Set the window size based on the screen bounds
        primaryStage.setX(wBounds.getMinX());
        primaryStage.setY(wBounds.getMinY());
        primaryStage.setWidth(wBounds.getWidth());
        primaryStage.setHeight(wBounds.getHeight());

        // Set items in the border into the scene and display the scene
        root.getChildren().add(dashboard);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        db = new DataBase();
        db.startUp();
        db.closeConnection();
        //DON'T PUT THINGS HERE. EVERYTHING SHOULD BE CREATED IN THE START FUNCTION
        launch(args);
    }
}
