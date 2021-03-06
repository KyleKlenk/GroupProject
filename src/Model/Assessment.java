package Model;

public class Assessment {

    private String title;
    private String courseTitle;
    private double mark;
    private int day;
    private int month;
    private int year;
    private String description;
    private double weight;

    public Assessment(String title, String courseTitle, double mark, int day, int month, int year,
                      String description, double weight){
        this.title = title;
        this.courseTitle = courseTitle;
        this.mark = mark;
        this.day = day;
        this.month = month;
        this.year = year;
        this.description = description;
        this.weight = weight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "title='" + title + '\'' +
                ", courseTitle=" + courseTitle +
                ", mark=" + mark +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                '}';
    }

    public boolean equalsByField(Assessment assessment) {
        return this.getTitle().equals(assessment.getTitle()) &&
                this.getCourseTitle().equals(assessment.getCourseTitle()) &&
                this.getMark() == assessment.getMark() &&
                this.getWeight() == assessment.getWeight();
    }
}
