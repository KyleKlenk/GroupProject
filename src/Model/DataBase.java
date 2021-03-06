package Model;

import java.sql.*;

public class DataBase {
    protected static Connection con;

    public void startUp() {
        getConnection();
    }

    public void setConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:plannerDB.db");
        } catch(ClassNotFoundException | SQLException e){
            System.out.println("Problem with setConnection");
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try{
            con.close();
        } catch (SQLException e){
            System.out.println("Problem closing connection");
            e.printStackTrace();
        }
    }

    protected void getConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:plannerDB.db");
            initialize();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Problem with getConnection");
            e.printStackTrace();
        }
    }

    private void initialize() {
        Statement state = null;
        try {
            state = con.createStatement();
            ResultSet courseTable = state.executeQuery("SELECT name FROM sqlite_master " +
                    "WHERE type='table' AND name='course';");

            if (!courseTable.next()) {
                state.execute("CREATE TABLE course" +
                        "(courseID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "courseName VARCHAR," +
                        "courseInstructor VARCHAR, " +
                        "courseDescription VARCHAR);");
                insertCourse("Default", "N/A", "N/A");
            }
        } catch (SQLException e) {
            System.out.println("Problem creating the course table");
            e.printStackTrace();
        }
        try {
            ResultSet eventTable = state.executeQuery("SELECT name FROM sqlite_master WHERE " +
                    "type='table' AND name='event';");
            if (!eventTable.next()){
                state.execute("CREATE TABLE event" +
                        "(eventID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "courseID INTEGER," +
                        "startTime VARCHAR," +
                        "endTime VARCHAR," +
                        "day INTEGER," +
                        "month INTEGER," +
                        "year INTEGER," +
                        "colorRedInt INTEGER," +
                        "colorGreenInt INTEGER," +
                        "colorBlueInt INTEGER," +
                        "eventTitle VARCHAR," +
                        "eventDescription VARCHAR," +
                        "eventLocation VARCHAR," +
                        "FOREIGN KEY(courseID) REFERENCES course(courseID)" +
                        "ON DELETE CASCADE);");
            }
        } catch (SQLException e) {
            System.out.println("Problem creating the event table");
            e.printStackTrace();
        }
        try {
            ResultSet assessmentTable = state.executeQuery("SELECT name FROM sqlite_master WHERE " +
                    "type='table' AND name='assessment';");
            if(!assessmentTable.next()) {
                state.execute("CREATE TABLE assessment" +
                        "(assessmentID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "courseID INTEGER," +
                        "weight REAL," +
                        "grade REAL," +
                        "assessmentTitle VARCHAR," +
                        "description VARCHAR," +
                        "day INTEGER," +
                        "month INTEGER," +
                        "year INTEGER," +
                        "FOREIGN KEY(courseID) REFERENCES course(courseID)" +
                        "ON DELETE CASCADE);");
            }
        } catch (SQLException e){
            System.out.println("Problem creating the assessment Table");
            e.printStackTrace();
        }
        try {
            ResultSet taskTable = state.executeQuery("SELECT name FROM sqlite_master WHERE " +
                    "type='table' AND name='task';");
            if(!taskTable.next()) {
                state.execute("CREATE TABLE task" +
                        "(taskID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "taskTitle VARCHAR," +
                        "taskDescription VARCHAR," +
                        "courseID INTEGER," +
                        "colorRedInt INTEGER," +
                        "colorGreenInt INTEGER," +
                        "colorBlueInt INTEGER," +
                        "dueDay INTEGER," +
                        "dueMonth INTEGER," +
                        "dueYear INTEGER," +
                        "dueTime VARCHAR," +
                        "completed VARCHAR," +
                        "FOREIGN KEY(courseID) REFERENCES course(courseID)" +
                        "ON DELETE CASCADE);");
            }
        } catch (SQLException e) {
            System.out.println("Problem creating task table");
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Problem closing after initializing");
            e.printStackTrace();
        }
    }

    public void insertCourse(String courseName, String courseInstructor, String courseDescription){
        PreparedStatement prep = null;
        try {
            setConnection();
            prep = con.prepareStatement("INSERT INTO course(courseName, " +
                    "courseInstructor, courseDescription) VALUES(?,?,?);");
            prep.setString(1, courseName);
            prep.setString(2, courseInstructor);
            prep.setString(3, courseDescription);
            prep.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem inserting course");
            e.printStackTrace();
        }
        finally {
            try {
                prep.close();
            } catch (SQLException e) {
                System.out.println("Problem closing insert course prepared statement");
                e.printStackTrace();
            }
        }
    }

    public void deleteCourse(String courseName) {
        PreparedStatement prep = null;
        try {
            setConnection();
            prep = con.prepareStatement("DELETE FROM course WHERE courseName = ?");
            prep.setString(1, courseName);
            prep.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem deleting course form data base");
            e.printStackTrace();
        } finally {
            try {
                prep.close();
            } catch (SQLException e) {
                System.out.println("Problem closing deleteCourse prepared statement");
                e.printStackTrace();
            }
        }
    }

    public ResultSet getAllCourses() {
        ResultSet resultQuery = null;
        try {
            setConnection();
            Statement state = con.createStatement();
            resultQuery = state.executeQuery("SELECT * FROM course");
        } catch (SQLException e) {
            System.out.println("Problem returning all courses");
            e.printStackTrace();
        }
        return resultQuery;
    }

    public void insertEvent(String courseName, String startTime, String endTime,
                            int day, int month, int year, int colorRedInt, int colorGreenInt, int colorBlueInt,
                            String eventTitle, String eventDescription ,
                            String eventLocation){
        PreparedStatement prep = null;
        try {
            setConnection();
            prep = con.prepareStatement("INSERT INTO event(courseID, startTime, endTime, day," +
                    " month, year, colorRedInt, colorGreenInt, colorBlueInt, eventTitle, " +
                    "eventDescription, eventLocation) VALUES ((SELECT courseID from course where courseName = ?)" +
                    ",?,?,?,?,?,?,?,?,?,?,?);");
            prep.setString(1, courseName);
            prep.setString(2, startTime);
            prep.setString(3, endTime);
            prep.setInt(4, day);
            prep.setInt(5, month);
            prep.setInt(6, year);
            prep.setInt(7, colorRedInt);
            prep.setInt(8, colorGreenInt);
            prep.setInt(9, colorBlueInt);
            prep.setString(10, eventTitle);
            prep.setString(11, eventDescription);
            prep.setString(12, eventLocation);
            prep.executeUpdate();
        } catch(SQLException e) {
        System.out.println("Problem inserting Event");
        e.printStackTrace();
        }
        finally {
            try {
            prep.close();
            } catch (SQLException e) {
            System.out.println("Problem closing prepared statement for insert");
            e.printStackTrace();
            }
        }
    }

    public void deleteEvent(String courseName, String startTime, String endTime,
                            int day, int month, int year, int colorRedInt, int colorGreenInt, int colorBlueInt,
                            String eventTitle, String eventDescription ,
                            String eventLocation){
        PreparedStatement prep = null;
        try {
            setConnection();
            prep = con.prepareStatement("DELETE FROM event WHERE " +
                    "startTime = ? AND endTime = ? AND day = ? AND month = ? AND year = ? " +
                    "AND colorRedInt = ? AND colorGreenInt = ? AND colorBlueInt = ? " +
                    "AND eventTitle = ? AND eventDescription = ? AND eventLocation = ? " +
                    "AND (SELECT courseID from course where courseName = ?);");
            prep.setString(1, startTime);
            prep.setString(2, endTime);
            prep.setInt(3, day);
            prep.setInt(4, month);
            prep.setInt(5, year);
            prep.setInt(6, colorRedInt);
            prep.setInt(7, colorGreenInt);
            prep.setInt(8, colorBlueInt);
            prep.setString(9, eventTitle);
            prep.setString(10, eventDescription);
            prep.setString(11, eventLocation);
            prep.setString(12, courseName);
            prep.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem deleting event");
            e.printStackTrace();
        }
        finally {
            try {
                prep.close();
            } catch (SQLException e) {
                System.out.println("Problem closing deleting event query");
                e.printStackTrace();
            }
        }
    }

    public ResultSet getAllEvents() {
        ResultSet resultQuery = null;
        try {
            setConnection();
            Statement state = con.createStatement();
            resultQuery = state.executeQuery("SELECT e.eventTitle," +
                    "e.eventDescription," +
                    "c.courseName," +
                    "e.day," +
                    "e.month," +
                    "e.year," +
                    "e.startTime," +
                    "e.endTime," +
                    "e.colorRedInt," +
                    "e.colorGreenInt," +
                    "e.colorBlueInt," +
                    "e.eventLocation " +
                    "FROM event e " +
                    "INNER JOIN course c on e.courseID = c.courseID;");
        } catch (SQLException e) {
            System.out.println("Problem in getting all Events");
            e.printStackTrace();
        }

        return resultQuery;
    }

    public ResultSet getNumEventsSpecificDay(int year, int month, int day) {
        ResultSet resultQuery = null;
        try {
            setConnection();
            PreparedStatement prep= con.prepareStatement("SELECT COUNT(*) AS rowcount FROM event " +
                    "WHERE year = ? AND month = ? AND day = ?; ");
            prep.setInt(1, year);
            prep.setInt(2, month);
            prep.setInt(3, day);
            resultQuery = prep.executeQuery();
        } catch (SQLException e) {
            System.out.println("Problem getting number of events");
            e.printStackTrace();
        }

        return resultQuery;
    }

    public ResultSet getEvents(int year, int month, int day) {
        ResultSet resultQuery = null;
        try {
            setConnection();
            PreparedStatement prep = con.prepareStatement(
                    "SELECT e.eventTitle," +
                            "e.eventDescription," +
                            "c.courseName," +
                            "e.day," +
                            "e.month," +
                            "e.year," +
                            "e.startTime," +
                            "e.endTime," +
                            "e.colorRedInt," +
                            "e.colorGreenInt," +
                            "e.colorBlueInt," +
                            "e.eventLocation " +
                            "FROM event e " +
                            "INNER JOIN course c on e.courseID = c.courseID " +
                            "WHERE year = ? AND month = ? AND day = ?; ");
            prep.setInt(1, year);
            prep.setInt(2, month);
            prep.setInt(3, day);
            resultQuery = prep.executeQuery();

        } catch(SQLException e){
            System.out.println("Problem with getDaysEvents");
            e.printStackTrace();
        }

        return resultQuery;
    }

    public void insertAssessment(String courseName, double weight, double grade, String assessmentTitle, String description,
                                 int day, int month, int year) {
        PreparedStatement prep = null;
        try {
            setConnection();
            prep = con.prepareStatement("INSERT INTO assessment(courseID, WEIGHT, " +
                    "GRADE, ASSESSMENTTITLE, description, day, month, year) VALUES(" +
                    "(SELECT courseID from course where courseName = ?)" +
                    ",?,?,?,?,?,?,?);");
            prep.setString(1, courseName);
            prep.setDouble(2, weight);
            prep.setDouble(3, grade);
            prep.setString(4, assessmentTitle);
            prep.setString(5, description);
            prep.setInt(6, day);
            prep.setInt(7, month);
            prep.setInt(8, year);
            prep.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Problem inserting Assessment");
            e.printStackTrace();
        }
        finally {
            try {
                prep.close();
            } catch (SQLException e) {
                System.out.println("Problem closing prepared statement for insertAssessment");
                e.printStackTrace();
            }
        }
    }

    public void deleteAssessment(String courseName, double weight, double grade, String assessmentTitle,
                                 String description, int day, int month, int year) {
        PreparedStatement prep = null;
        try {
            setConnection();
            prep = con.prepareStatement("DELETE FROM assessment WHERE " +
                    "weight = ? AND grade = ? AND assessmentTitle = ? AND description = ? " +
                    "AND day = ? AND month = ? AND year = ? AND" +
                    "(SELECT courseID from course where courseName = ?);");
            prep.setDouble(1, weight);
            prep.setDouble(2, grade);
            prep.setString(3, assessmentTitle);
            prep.setString(4, description);
            prep.setInt(5, day);
            prep.setInt(6, month);
            prep.setInt(7, year);
            prep.setString(8, courseName);
            prep.executeUpdate();
        } catch (SQLException e){
            System.out.println("Problem deleting assessment from dataBase");
            e.printStackTrace();
        } finally {
            try {
                prep.close();
            } catch (SQLException e) {
                System.out.println("Problem closing deleteAssessment prepared statement");
            }
        }
    }

    public ResultSet getSpecificCourseAssessments(String courseName) {
        ResultSet resultQuery = null;
        try {
            setConnection();
            PreparedStatement prep = con.prepareStatement("SELECT * FROM assessment WHERE courseID = " +
                    "(SELECT courseID FROM course WHERE courseName = ?);");
            prep.setString(1, courseName);
            resultQuery = prep.executeQuery();
        } catch (SQLException e) {
            System.out.println("Problem getting assessment with specific course");
            e.printStackTrace();
        }
        return resultQuery;
    }

    public void insertTask(String taskTitle, String taskDescription, String courseName,
                           int colorRedInt, int colorGreenInt, int colorBlueInt,
                           int dueDay, int dueMonth, int dueYear, String dueTime) {
        PreparedStatement prep = null;
        try {
            setConnection();
            prep = con.prepareStatement("INSERT INTO task(taskTitle, taskDescription, courseID, " +
                    "colorRedInt, colorGreenInt, colorBlueInt, dueDay, dueMonth, dueYear, dueTime) " +
                    "VALUES(?,?," +
                    "(SELECT courseID from course where courseName = ?)," +
                    "?,?,?,?,?,?,?);");
            prep.setString(1, taskTitle);
            prep.setString(2, taskDescription);
            prep.setString(3, courseName);
            prep.setInt(4, colorRedInt);
            prep.setInt(5, colorGreenInt);
            prep.setInt(6, colorBlueInt);
            prep.setInt(7, dueDay);
            prep.setInt(8, dueMonth);
            prep.setInt(9, dueYear);
            prep.setString(10, dueTime);
            prep.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Problem inserting Task");
            e.printStackTrace();
        } finally {
            try {
                prep.close();
            } catch (SQLException e) {
                System.out.println("Problem closing task statement ");
            }
        }
    }

    public void deleteTask(String taskTitle, String taskDescription, String courseName,
                           int colorRedInt, int colorGreenInt, int colorBlueInt,
                           int dueDay, int dueMonth, int dueYear, String dueTime) {
        PreparedStatement prep = null;
        try {
            setConnection();
            prep = con.prepareStatement("DELETE FROM task WHERE " +
                    "taskTitle = ? AND taskDescription = ? AND colorRedInt = ? " +
                    "AND colorGreenInt = ? AND colorBlueInt = ? " +
                    "AND dueDay = ? AND dueMonth = ? AND dueYear = ? AND dueTime = ? " +
                    "AND (SELECT courseID from course where courseName = ?)");
            prep.setString(1, taskTitle);
            prep.setString(2, taskDescription);
            prep.setInt(3, colorRedInt);
            prep.setInt(4, colorGreenInt);
            prep.setInt(5, colorBlueInt);
            prep.setInt(6, dueDay);
            prep.setInt(7, dueMonth);
            prep.setInt(8, dueYear);
            prep.setString(9, dueTime);
            prep.setString(10, courseName);
            prep.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem removing task from database");
            e.printStackTrace();
        } finally {
            try {
                prep.close();
            } catch (SQLException e) {
                System.out.println("Problem closing deleteTask prepared statement");
            }
        }
    }

    public ResultSet getAllTasks()  {
        ResultSet resultQuery = null;
        try {
            setConnection();
            Statement state = con.createStatement();
            resultQuery = state.executeQuery("SELECT " +
                    "t.taskTitle," +
                    "t.taskDescription," +
                    "c.courseName," +
                    "t.colorRedInt," +
                    "t.colorBlueInt," +
                    "t.colorGreenInt," +
                    "t.dueDay," +
                    "t.dueMonth," +
                    "t.dueYear," +
                    "t.dueTime " +
                    "FROM task t " +
                    "INNER JOIN course c on t.courseID = c.courseID;");
        } catch (SQLException e) {
            System.out.println("Problem getting all tasks");
            e.printStackTrace();
        }
        return resultQuery;

    }
}
