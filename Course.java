public class Course {
    private int id;
    private String courseName;
    private String courseDescription;

    public Course() {}

    public Course(int id, String courseName, String courseDescription) {
        this.id = id;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public Course(String courseName, String courseDescription) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public int getId() { return id; }
    public String getCourseName() { return courseName; }
    public String getCourseDescription() { return courseDescription; }

    public void setId(int id) { this.id = id; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }
}