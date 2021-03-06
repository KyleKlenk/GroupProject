package Model;

import java.awt.*;

import View.PlannerListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class Calendar {
   private ArrayList<PlannerListener> subscribers;
   private int selectedDay;
   private int selectedMonth;
   private int selectedYear;
   private ArrayList<Event> currentDayEvents;
   private DataBase db;

   public Calendar() {
       subscribers = new ArrayList<>();
       this.selectedDay = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
       this.selectedMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;
       this.selectedYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
       this.db = new DataBase();
       this.currentDayEvents = new ArrayList<Event>();
   }

   public void setSelectedDay(LocalDate date){
       selectedDay = date.getDayOfMonth();
       selectedMonth = date.getMonthValue();
       selectedYear = date.getYear();
       currentDayEvents = getEvents();
       notifySubscribers();
   }

   public LocalDate getSelectedDay(){
       LocalDate date = LocalDate.of(selectedYear, selectedMonth, selectedDay);
       return date;
   }

   public void changeMonthBy(int increment) {
       selectedMonth += increment;
       if(selectedMonth < 1){
           selectedMonth = 12;
           selectedYear -= 1;
       }
       else if(selectedMonth > 12){
            selectedMonth = 1;
            selectedYear += 1;
       }
   }

   public void updateEvents() {
       currentDayEvents = getEvents();
       notifySubscribers();
   }

   private ArrayList<Event> formatEventQuery(ResultSet query, ArrayList<Event> events) {
       try {
           while (query.next()) {
               SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
               String startTimeString = query.getString("startTime");
               String endTimeString = query.getString("endTime");

               Time startTime = new Time(format1.parse(startTimeString).getTime());
               Time endTime = new Time(format1.parse(endTimeString).getTime());

               Color eventColor = new Color(query.getInt("colorRedInt"),
                                            query.getInt("colorGreenInt"),
                                            query.getInt("colorBlueInt"));

               Event event = new Event(query.getString("eventTitle"),
                       query.getString("eventDescription"),
                       query.getString("courseName"),
                       eventColor,
                       query.getInt("day"),
                       query.getInt("month"),
                       query.getInt("year"),
                       startTime,
                       endTime,
                       query.getString("eventLocation"));
               events.add(event);
           }
       } catch (SQLException | ParseException e) {
           System.out.println("Problem with formatEventQuery");
           e.printStackTrace();
       }
       finally {
           try {
               query.close();
           } catch (SQLException e) {
               System.out.println("Problem Closing formatEventQuery");
               e.printStackTrace();
           }
       }
       db.closeConnection();
       return events;
   }
   // method for testing purposes
   public ArrayList<Event> getAllEvents() {
       ResultSet eventsQuery = db.getAllEvents();
       ArrayList<Event> events = new ArrayList<Event>();
       return formatEventQuery(eventsQuery, events);
   }

   public ArrayList<Event> getEvents() {
       ResultSet eventsQuery = db.getEvents(selectedYear, selectedMonth, selectedDay);
       ArrayList<Event> events = new ArrayList<Event>();
       return formatEventQuery(eventsQuery, events);
   }

   public int getNumEventsSpecificDay(int year, int month, int day) {
       ResultSet eventsQuery = db.getNumEventsSpecificDay(year, month, day);
       int numEvents = 0;
       try {
           eventsQuery.next();
           numEvents = eventsQuery.getInt("rowcount");
       } catch (SQLException e) {
           System.out.println("problem getting rowcount");
           e.printStackTrace();
       } finally {
           try {
               eventsQuery.close();
           } catch (SQLException e) {
               System.out.println("problem closing events query");
           }
       }
       return numEvents;
   }

   public void insertEvent(Event userInput) {
       db.insertEvent(userInput.getCourseName(), userInput.getStart().toString(), userInput.getEnd().toString(),
               userInput.getDay(), userInput.getMonth(), userInput.getYear(), userInput.getColor().getRed(),
               userInput.getColor().getGreen(), userInput.getColor().getBlue(), userInput.getTitle(),
               userInput.getDescription(), userInput.getLocation());
       db.closeConnection();
       currentDayEvents = getEvents();
       notifySubscribers();
   }

   public void deleteEvent(Event userInput) {
       db.deleteEvent(userInput.getCourseName(), userInput.getStart().toString(), userInput.getEnd().toString(),
               userInput.getDay(), userInput.getMonth(), userInput.getYear(), userInput.getColor().getRed(),
               userInput.getColor().getGreen(), userInput.getColor().getBlue(), userInput.getTitle(),
               userInput.getDescription(), userInput.getLocation());
       db.closeConnection();
       notifySubscribers();
   }

   public void addSubscriber (PlannerListener aSub) {
       subscribers.add(aSub);
   }

   private void notifySubscribers() {
        subscribers.forEach(PlannerListener::modelChanged);
   }

}
