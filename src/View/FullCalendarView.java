package View;

import Controller.CalendarController;
import Model.Calendar;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;


public class FullCalendarView implements PlannerListener {

    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;
    private CalendarController controller;
    private Calendar model;

    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public FullCalendarView(YearMonth yearMonth, CalendarController controller) {
        currentYearMonth = yearMonth;
        this.controller = controller;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(600, 400);
        calendar.setGridLinesVisible(true);
       //System.out.println(yearMonth);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setController(this.controller);
                ap.setOnClickController();
                ap.setPrefSize(200,200);
                calendar.add(ap,j,i);
                allCalendarDays.add(ap);
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{ new Text("Sunday"), new Text("Monday"), new Text("Tuesday"),
                                        new Text("Wednesday"), new Text("Thursday"), new Text("Friday"),
                                        new Text("Saturday") };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(actionEvent -> {
            try {
                previousMonth(actionEvent);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(actionEvent -> {
            try {
                nextMonth(actionEvent);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth) {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().clear();
            }
            Text day = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            if(model.getSelectedDay().compareTo(calendarDate) == 0){
                ap.setBackground(new Background(new BackgroundFill(Color.rgb(204, 204, 255),
                        CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else if(LocalDate.now().compareTo(calendarDate) == 0){
                ap.setBackground(new Background(new BackgroundFill(Color.rgb(148, 148, 209),
                        CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else{
                ap.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,
                        CornerRadii.EMPTY, Insets.EMPTY)));
            }
            ap.setDate(calendarDate);
            int count = model.getNumEventsSpecificDay(calendarDate.getYear(),
                    calendarDate.getMonthValue(), calendarDate.getDayOfMonth());
            ap.setTopAnchor(day, 5.0);
            ap.setLeftAnchor(day, 5.0);
            ap.getChildren().add(day);
            if(count > 0){
                Text eventCount = new Text("Events: " + String.valueOf(count));
                eventCount.setBoundsType(TextBoundsType.VISUAL);
                ap.setRightAnchor(eventCount, 10.0);
                ap.setBottomAnchor(eventCount, 10.0);
                ap.getChildren().add(eventCount);
            }

            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth(ActionEvent actionEvent) throws ParseException, SQLException, ClassNotFoundException {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth);
        controller.previousMonthClicked(actionEvent);
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth(ActionEvent actionEvent) throws ParseException, SQLException, ClassNotFoundException {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth);
        controller.nextMonthClicked(actionEvent);
    }

    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }

    public void setController(CalendarController ctrl) {
        controller = ctrl;
    }

    public void setModel(Calendar m){
        model = m;
    }

    public void modelChanged() {
        populateCalendar(currentYearMonth);
    }
}
