import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    //(Adding a new course)
    public void addCourse(Course course) {
        String sql = "INSERT INTO Courses (course_name, course_description) VALUES (?, ?)";
        try (Connection conn = ConnectToDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseDescription());
            pstmt.executeUpdate();
            System.out.println("Course added successfully.");

        } catch (SQLException e) {
            System.err.println("Error "+ e.getMessage());
        }
    }

    //(Get all courses)
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Courses";
        try (Connection conn = ConnectToDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("id"),
                    rs.getString("course_name"),
                    rs.getString("course_description")
                );
                courses.add(course);
            }

        } catch (SQLException e) {
            System.err.println("Error "+ e.getMessage());
        }
        return courses;
    }

    //(Get course by ID)
    public Course getCourseById(int id) {
        String sql = "SELECT * FROM Courses WHERE id = ?";
        try (Connection conn = ConnectToDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                        rs.getInt("id"),
                        rs.getString("course_name"),
                        rs.getString("course_description")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error "+ e.getMessage());
        }
        return null;
    }

    //(Update course name or description)
    public void updateCourse(Course course) {
        String sql = "UPDATE Courses SET course_name = ?, course_description = ? WHERE id = ?";
        try (Connection conn = ConnectToDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseDescription());
            pstmt.setInt(3, course.getId());

            int updated = pstmt.executeUpdate();
            if (updated > 0) {
                System.out.println("Course updated successfully.");
            } else {
                System.out.println("No course found with ID " + course.getId());
            }

        } catch (SQLException e) {
            System.err.println("Error "+ e.getMessage());
        }
    }

    //Removing a course (Marks will cascade if Foreign Key is set with ON DELETE CASCADE)
    public void deleteCourse(int id) {
        String sql = "DELETE FROM Courses WHERE id = ?";
        try (Connection conn = ConnectToDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int deleted = pstmt.executeUpdate();
            if (deleted > 0) {
                System.out.println("Course deleted successfully.");
            } else {
                System.out.println("No course found with ID " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error "+ e.getMessage());
        }
    }
}