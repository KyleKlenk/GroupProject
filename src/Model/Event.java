package Model;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.Time;

//test
public class Event extends CalendarItem {
    protected Time start;
    protected Time end;
    protected int day;
    protected int month;
    protected int year;
    protected String location;

    public Event(String title, String description, String courseName, Color color,
                 int day, int month, int year, Time start, Time end, String location) {
        super(title, description, courseName, color);
        this.start = start;
        this.end = end;
        this.day = day;
        this.month = month;
        this.year = year;
        this.location = location;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getLocation() {
        return location;
    }


    public void setStart(Time start) {
        this.start = start;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Event{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", course='" + getCourseName() + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", color="+ super.getColor() +
                '}';
    }

    public boolean equalsByField(Event event) {
        return this.getTitle().equals(event.getTitle()) &&
                this.getDescription().equals(event.getDescription()) &&
                this.getStart().equals(event.getStart()) &&
                this.getEnd().equals(event.getEnd());
    }

}
