public class Marks {
    private int studentId;
    private int courseId;
    private float marks;

    public Marks() {}

    public Marks(int studentId, int courseId, float marks) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.marks = marks;
    }

    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public float getMarks() { return marks; }

    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setMarks(float marks) { this.marks = marks; }
}
