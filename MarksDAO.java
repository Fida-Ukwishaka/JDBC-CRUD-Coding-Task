import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarksDAO {

    //(Assign or update a mark for a student in a course)
    public void assignOrUpdateMark(Marks mark) {
        String sql = "INSERT INTO Marks (student_id, course_id, marks) VALUES (?, ?, ?) " +
                     "ON CONFLICT (student_id, course_id) DO UPDATE SET marks = EXCLUDED.marks";
        try (Connection conn = ConnectToDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mark.getStudentId());
            pstmt.setInt(2, mark.getCourseId());
            pstmt.setFloat(3, mark.getMarks());
            pstmt.executeUpdate();
            System.out.println("Mark assigned/updated successfully.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //(Get all marks)
    public List<Marks> getAllMarks() {
        List<Marks> marksList = new ArrayList<>();
        String sql = "SELECT * FROM Marks";
        try (Connection conn = ConnectToDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Marks mark = new Marks(
                    rs.getInt("student_id"),
                    rs.getInt("course_id"),
                    rs.getFloat("marks")
                );
                marksList.add(mark);
            }

        } catch (SQLException e) {
            System.out.println("Error: "+ e.getMessage());
        }
        return marksList;
    }

    //Get mark by student_id and course_id (composite key)
    public Marks getMark(int studentId, int courseId) {
        String sql = "SELECT * FROM Marks WHERE student_id = ? AND course_id = ?";
        try (Connection conn = ConnectToDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Marks(
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getFloat("marks")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: "+ e.getMessage());
        }
        return null;
    }

    //(Remove a student's mark in a course)
    public void deleteMark(int studentId, int courseId) {
        String sql = "DELETE FROM Marks WHERE student_id = ? AND course_id = ?";
        try (Connection conn = ConnectToDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            int deleted = pstmt.executeUpdate();
            if (deleted > 0) {
                System.out.println("Mark deleted successfully.");
            } else {
                System.out.println("No mark found for student ID " + studentId + " and course ID " + courseId);
            }

        } catch (SQLException e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }
}
