import Model.*;
import Model.Event;
import org.junit.*;


import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import static org.junit.Assert.*;

public class MainTest {
    private static DataBase db;
    private static CoursesModel courseModel;
    private static Calendar calendarModel;
    private static TaskBoardModel taskModel;
    public static Course course1;
    public static Course course2;
    public static Course course3;
    public static Assessment assessment1;
    public static Assessment assessment2;
    public static Assessment assessment3;
    public static Assessment assessment4;
    public static Assessment assessment5;
    public static Assessment assessment6;
    public static Assessment assessment7;
    public static Assessment assessment8;
    public static Assessment assessment9;
    public static Assessment assessment10;
    public static Event event1;
    public static Event event2;
    public static Event event3;
    public static Event event4;
    public static Event event5;
    public static Event event6;
    public static Event event7;
    public static Event event8;
    public static Event event9;
    public static Event event10;
    public static Task task1;
    public static Task task2;
    public static Task task3;
    public static Task task4;
    public static Task task5;
    public static Task task6;
    public static Task task7;
    public static Task task8;
    public static Task task9;
    public static Task task10;



    @BeforeClass
    public static void setup() {
        // Make sure to start with a new database
        db = new DataBase();
        db.startUp();
        calendarModel = new Calendar();
        taskModel = new TaskBoardModel();
        courseModel = new CoursesModel(calendarModel, taskModel);
        /**
         * Inserting Courses, Tests will check if succeeded
         */
        course1 = new Course("CMPT370", "Software Engineering", "Kevin");
        course2 = new Course("CMPT360", "Algorithms","Mondal");
        course3 = new Course("CMPT340", "Programming Paradigms", "Nadeem");
        courseModel.insertCourse(course1);
        courseModel.insertCourse(course2);
        courseModel.insertCourse(course3);
        /**
         * Inserting Assessments, Tests will check if succeeded
         */
        assessment1 = new Assessment("A1", "CMPT340", 35, 1,1,1,
                " ", 10);
        assessment2 = new Assessment("A2", "CMPT340", 75, 1,1,1,
                " ", 10);
        assessment3 = new Assessment("A3", "CMPT340", 87, 1,1,1,
                " ", 10);
        assessment4 = new Assessment("A4", "CMPT340", 88, 1,1,1,
                " ", 10);
        assessment5 = new Assessment("A5", "CMPT340", 60, 1,1,1,
                " ", 10);
        assessment6 = new Assessment("final", "CMPT340", 55, 1,1,1,
                " ", 50);
        assessment7 = new Assessment("A1", "CMPT360", 60, 1,1,1,
                " ", 15);
        assessment8 = new Assessment("A2", "CMPT360", 65, 1,1,1,
                " ", 25);
        assessment9 = new Assessment("A1", "CMPT370", 100, 1,1,1,
                " ", 50);
        assessment10 = new Assessment("A2", "CMPT370", 100, 1,1,1,
                " ", 25);
        courseModel.insertAssessment(assessment1);
        courseModel.insertAssessment(assessment2);
        courseModel.insertAssessment(assessment3);
        courseModel.insertAssessment(assessment4);
        courseModel.insertAssessment(assessment5);
        courseModel.insertAssessment(assessment6);
        courseModel.insertAssessment(assessment7);
        courseModel.insertAssessment(assessment8);
        courseModel.insertAssessment(assessment9);
        courseModel.insertAssessment(assessment10);
        // Testing to create a lot of assignments so we have to scroll through them
        Course course4 = new Course("MATH364", "Number Theory", "Cam");
        //ArrayList<Assessment> lotsOfAssessments = new ArrayList<>();
        courseModel.insertCourse(course4);
        for (int i = 0 ; i < 50; i++) {
            String title = "" + i;
            Assessment assessment = new Assessment(title, "MATH364", 1,1,1,1,
            " ", 1);
            courseModel.insertAssessment(assessment);
        }
        /**
         * Inserting Events
         */
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        String startTimeString = "9:30";
        String endTimeString = "12:30";
        Time startTime;
        Time endTime;
        try {
            startTime = new Time(format1.parse(startTimeString).getTime());
            endTime = new Time(format1.parse(endTimeString).getTime());
        } catch (ParseException e) {
            System.out.println("Problem in parsing dates for testing events, setting time to null");
            startTime = null;
            endTime = null;
        }
        event1 = new Event("Class1", "test1", "CMPT340", Color.GREEN, 5,
                4, 2020, startTime, endTime, "Thorv");
        event2 = new Event("Class2", "test2", "CMPT340", Color.GREEN, 8,
                4, 2020, startTime, endTime, "Thorv");
        event3 = new Event("Class3", "test3", "CMPT340", Color.GREEN, 5,
                4, 2020, startTime, endTime, "Thorv");
        event4 = new Event("Class4", "test4", "CMPT370", Color.BLUE, 8,
                4, 2020, startTime, endTime, "Arts");
        event5 = new Event("Class5", "test5", "CMPT370", Color.BLUE, 5,
                4, 2020, startTime, endTime, "Arts");
        event6 = new Event("Class6", "test6", "CMPT370", Color.BLUE, 8,
                4, 2020, startTime, endTime, "Arts");
        event7 = new Event("Class7", "test7", "CMPT360", Color.RED, 7,
                4, 2020, startTime, endTime, "Physics");
        event8 = new Event("Class8", "test8", "CMPT360", Color.RED, 9,
                4, 2020, startTime, endTime, "Physics");
        event9 = new Event("Class9", "test9", "CMPT360", Color.ORANGE, 9,
                4, 2020, startTime, endTime, "Arts");
        event10 = new Event("Class10", "test10", "CMPT370", Color.ORANGE, 7,
                4, 2020, startTime, endTime, "Arts");
        calendarModel.insertEvent(event1);
        calendarModel.insertEvent(event2);
        calendarModel.insertEvent(event3);
        calendarModel.insertEvent(event4);
        calendarModel.insertEvent(event5);
        calendarModel.insertEvent(event6);
        calendarModel.insertEvent(event7);
        calendarModel.insertEvent(event8);
        calendarModel.insertEvent(event9);
        calendarModel.insertEvent(event10);
        /**
         * Inserting tasks
         */
        String dueTimeString = "18:00";
        Time dueTime;
        try {
            dueTime = new Time(format1.parse(dueTimeString).getTime());
        } catch(ParseException e) {
            System.out.println("Problem setting dueTime, Setting dueTime to null");
            dueTime = null;
        }
        task1 = new Task("task1", "test1", "CMPT340", Color.GREEN,
                7, 4, 2020, dueTime);
        task2 = new Task("task2", "test2", "CMPT340", Color.GREEN,
                7, 4, 2020, dueTime);
        task3 = new Task("task3", "test3", "CMPT340", Color.GREEN,
                7, 4, 2020, dueTime);
        task4 = new Task("task4", "test4", "CMPT340", Color.GREEN,
                7, 4, 2020, dueTime);
        task5 = new Task("task5", "test5", "CMPT360", Color.RED,
                7, 4, 2020, dueTime);
        task6 = new Task("task6", "test6", "CMPT360", Color.RED,
                7, 4, 2020, dueTime);
        task7 = new Task("task7", "test7", "CMPT360", Color.RED,
                7, 4, 2020, dueTime);
        task8 = new Task("task8", "test8", "CMPT370", Color.BLUE,
                7, 4, 2020, dueTime);
        task9 = new Task("task9", "test9", "CMPT370", Color.BLUE,
                7, 4, 2020, dueTime);
        task10 = new Task("task10", "test10", "CMPT370", Color.BLUE,
                7, 4, 2020, dueTime);
        taskModel.insertTask(task1);
        taskModel.insertTask(task2);
        taskModel.insertTask(task3);
        taskModel.insertTask(task4);
        taskModel.insertTask(task5);
        taskModel.insertTask(task6);
        taskModel.insertTask(task7);
        taskModel.insertTask(task8);
        taskModel.insertTask(task9);
        taskModel.insertTask(task10);
    }

    @Test
    public void insertCoursesTest() {
        ArrayList<Course> coursesDB;
        coursesDB = courseModel.getCoursesFromDB();
        assertTrue(course1.equalsByField(coursesDB.get(1)));
        assertTrue(course2.equalsByField(coursesDB.get(2)));
        assertTrue(course3.equalsByField(coursesDB.get(3)));
    }

    @Test
    public void deleteCoursesTest() {
        ArrayList<Course> coursesDB;
        coursesDB = courseModel.getCoursesFromDB();
        ArrayList<Assessment> assessmentsDB;
        assessmentsDB = courseModel.getSpecificCourseAssessmentList("MATH364");

        assertEquals(5, coursesDB.size());
        assertEquals(50, assessmentsDB.size());

        db.deleteCourse("MATH364");
        coursesDB = courseModel.getCoursesFromDB();
        assessmentsDB = courseModel.getSpecificCourseAssessmentList("MATH364");

        assertEquals(4, coursesDB.size());
        assertEquals(0, assessmentsDB.size());



    }
    @Test
    public void insertAssessmentsTest() {
        ArrayList<Assessment> assessmentsDB;
        assessmentsDB = courseModel.getSpecificCourseAssessmentList("CMPT340");
        assertTrue(assessment1.equalsByField(assessmentsDB.get(0)));
        assertTrue(assessment2.equalsByField(assessmentsDB.get(1)));
        assertTrue(assessment3.equalsByField(assessmentsDB.get(2)));
        assertTrue(assessment4.equalsByField(assessmentsDB.get(3)));
        assertTrue(assessment5.equalsByField(assessmentsDB.get(4)));
        assertTrue(assessment6.equalsByField(assessmentsDB.get(5)));

        assessmentsDB = courseModel.getSpecificCourseAssessmentList("CMPT360");
        assertTrue(assessment7.equalsByField(assessmentsDB.get(0)));
        assertTrue(assessment8.equalsByField(assessmentsDB.get(1)));

        assessmentsDB = courseModel.getSpecificCourseAssessmentList("CMPT370");
        assertTrue(assessment9.equalsByField(assessmentsDB.get(0)));
        assertTrue(assessment10.equalsByField(assessmentsDB.get(1)));
    }

    @Test
    public void gradesTest() {
        courseModel.setSelectedCourse("CMPT340");
        courseModel.updateAssessmentList();
        assertEquals(62.0, courseModel.getAverageGrade(), 0.5);
        assertEquals(61.7, courseModel.getMinimumGrade(), 0.5);

        courseModel.setSelectedCourse("CMPT360");
        courseModel.updateAssessmentList();
        assertEquals(63.1, courseModel.getAverageGrade(), 0.5);
        assertEquals(25.25, courseModel.getMinimumGrade(), 0.5);

        courseModel.setSelectedCourse("CMPT370");
        courseModel.updateAssessmentList();
        assertEquals(100, courseModel.getAverageGrade(), 0.5);
        assertEquals(75.0, courseModel.getMinimumGrade(), 0.5);
    }

    @Test
    public void eventsTest() {
        ArrayList<Event> events = calendarModel.getAllEvents();
        assertEquals(10, events.size());
        assertTrue(event1.equalsByField(events.get(0)));
        assertTrue(event2.equalsByField(events.get(1)));
        assertTrue(event3.equalsByField(events.get(2)));
        assertTrue(event4.equalsByField(events.get(3)));
        assertTrue(event5.equalsByField(events.get(4)));
        assertTrue(event6.equalsByField(events.get(5)));
        assertTrue(event7.equalsByField(events.get(6)));
        assertTrue(event8.equalsByField(events.get(7)));
        assertTrue(event9.equalsByField(events.get(8)));
        assertTrue(event10.equalsByField(events.get(9)));

        calendarModel.deleteEvent(event1);
        events = calendarModel.getAllEvents();
        assertEquals(9, events.size());
        assertFalse(event1.equalsByField(events.get(0)));

        calendarModel.deleteEvent(event10);
        calendarModel.deleteEvent(event9);
        events = calendarModel.getAllEvents();
        assertEquals(7, events.size());
    }

    @Test
    public void tasksTest(){
        ArrayList<Task> tasks = taskModel.getTasksFromDB();
        assertEquals(10, tasks.size());
        assertTrue(task1.equalsByField(tasks.get(0)));
        assertTrue(task2.equalsByField(tasks.get(1)));
        assertTrue(task3.equalsByField(tasks.get(2)));
        assertTrue(task4.equalsByField(tasks.get(3)));
        assertTrue(task5.equalsByField(tasks.get(4)));
        assertTrue(task6.equalsByField(tasks.get(5)));
        assertTrue(task7.equalsByField(tasks.get(6)));
        assertTrue(task8.equalsByField(tasks.get(7)));
        assertTrue(task9.equalsByField(tasks.get(8)));
        assertTrue(task10.equalsByField(tasks.get(9)));

        taskModel.deleteTask(task1);
        tasks = taskModel.getTasksFromDB();
        assertEquals(9, tasks.size());
        assertFalse(task1.equalsByField(tasks.get(0)));

        taskModel.deleteTask(task8);
        taskModel.deleteTask(task10);
        tasks = taskModel.getTasksFromDB();
        assertEquals(7, tasks.size());
    }

    @AfterClass
    public static void cleanUp() {
        File file = new File("plannerDB.db");
        if (file.delete()) {
            System.out.println("Done testing deleting DataBase");
        } else {
            System.out.println("Failed to delete DataBase after testing.");
            System.out.println("Please manually delete the database from the directory");
        }
    }
}
