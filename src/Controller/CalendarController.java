package Controller;

import Model.Calendar;
import View.DaySidebar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import View.AnchorPaneNode;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;

public class CalendarController {
    // Get the pane to put the calendar on
    @FXML
    public Pane calendarPane;

    Calendar model;

    public void setModel(Calendar newModel) {
        model = newModel;
    }

    public void handleDayClicked(MouseEvent event) {
        LocalDate date = ((AnchorPaneNode)event.getSource()).getDate();
        System.out.println(date);
        model.setSelectedDay(date);
    }

    public void previousMonthClicked(ActionEvent actionEvent) {
        System.out.println("Go back a month");
        model.changeMonthBy(-1);
    }

    public void nextMonthClicked(ActionEvent actionEvent) {
        System.out.println("Go forward a month");
        model.changeMonthBy(+1);
    }
}
