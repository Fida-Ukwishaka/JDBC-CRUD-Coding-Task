import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    //(Add a new student)
    public void addStudent(Student student) {
        String sql = "INSERT INTO Students (first_name, last_name, email, date_of_birth) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectToDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setDate(4, student.getDateOfBirth() != null ? Date.valueOf(student.getDateOfBirth()) : null);
            pstmt.executeUpdate();

            System.out.println("Student added successfully.");

        } catch (SQLException e) {
            System.err.println("Error "+ e.getMessage());
        }
    }

    //(Get all students)
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Connection conn = ConnectToDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getDate("date_of_birth") != null ? rs.getDate("date_of_birth").toLocalDate() : null
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("Error "+ e.getMessage());
        }
        return students;
    }

    //(student by ID)
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM Students WHERE id = ?";
        try (Connection conn = ConnectToDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("date_of_birth") != null ? rs.getDate("date_of_birth").toLocalDate() : null
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error "+ e.getMessage());
        }
        return null;
    }

    //(update any student's field)
    public void updateStudent(Student student) {
        String sql = "UPDATE Students SET first_name = ?, last_name = ?, email = ?, date_of_birth = ? WHERE id = ?";
        try (Connection conn = ConnectToDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setDate(4, student.getDateOfBirth() != null ? Date.valueOf(student.getDateOfBirth()) : null);
            pstmt.setInt(5, student.getId());

            int updated = pstmt.executeUpdate();
            if (updated > 0) {
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("No student found with ID " + student.getId());
            }

        } catch (SQLException e) {
            System.err.println("Error "+ e.getMessage());
        }
    }

    //(Remove a student with all their data)
    public void deleteStudent(int id) {
        String sql = "DELETE FROM Students WHERE id = ?";
        try (Connection conn = ConnectToDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int deleted = pstmt.executeUpdate();
            if (deleted > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("No student found with ID " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error "+ e.getMessage());
        }
    }
}