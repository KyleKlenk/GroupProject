package Model;

public class Course {
    private String title;
    private String description;
    private String instructor;

    public Course(String title, String description, String instructor){
        this.title = title;
        this.description = description;
        this.instructor = instructor;
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

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructor='" + instructor + '\'' +
                '}';
    }

    public boolean equalsByField(Course course) {
        return this.getTitle().equals(course.getTitle()) &&
                this.getDescription().equals(course.getDescription()) &&
                this.getInstructor().equals(course.getInstructor());
    }

}
