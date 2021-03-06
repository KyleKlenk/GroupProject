package Controller;

import Model.Calendar;
import Model.Course;
import Model.CoursesModel;
import Model.Event;
import View.NewEventDialog;
import javafx.event.ActionEvent;
import javafx.scene.control.Dialog;

import java.util.ArrayList;
import java.util.Optional;

public class DayTabController {
    Calendar model;
    CoursesModel courseModel;
    Event eventToDelete;

    public void setModel(Calendar newModel) {
        model = newModel;
    }

    public void setCoursesModel(CoursesModel newModel) {
        courseModel = newModel;
    }

    public void setEventToDelete(Event event) {
        this.eventToDelete = event;
    }

    /**
     * Creates dialog to collect information for new event when New Event button clicked
     * @param actionEvent
     */
    public void handleAddEventClicked(ActionEvent actionEvent) {
        ArrayList<String> courseStrings = new ArrayList<>();
        ArrayList<Course> courses = courseModel.getCourseList();
        courseStrings.add("None");
        for(Course c : courses){
            courseStrings.add(c.getTitle());
        }

        Dialog<Event> dialog = new NewEventDialog(courseStrings);

        Optional<Event> result = dialog.showAndWait();

        result.ifPresent(event -> {

            model.insertEvent(event);
        });
    }

    public void handleRemoveEventClicked(ActionEvent actionEvent) {
        model.deleteEvent(eventToDelete);
    }
}



