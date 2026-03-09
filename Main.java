import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner inputs = new Scanner(System.in);
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        MarksDAO marksDAO = new MarksDAO();

        boolean running = true;

        while (running) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("a. Add Student");
            System.out.println("b. View Student");
            System.out.println("c. Update Student");
            System.out.println("d. Delete Student");
            System.out.println("e. Add Course");
            System.out.println("f. Get All Courses");
            System.out.println("g. View Course");
            System.out.println("h. Update Course");
            System.out.println("i. Delete Course");
            System.out.println("j. Add Mark");
            System.out.println("k. View Student Marks");
            System.out.println("l. Update Student Mark");
            System.out.println("m. Delete Student Mark");
            System.out.println("n. Exit");
            System.out.print("Choose an option: ");

            String choice = inputs.nextLine();

            switch (choice.toLowerCase()) {
                case "a": 
                    System.out.print("First Name: ");
                    String firstName = inputs.nextLine();
                    System.out.print("Last Name: ");
                    String lastName = inputs.nextLine();
                    System.out.print("Email: ");
                    String email = inputs.nextLine();
                    System.out.print("Date of Birth (YYYY-MM-DD): ");
                    String dob = inputs.nextLine();
                    studentDAO.addStudent(new Student(firstName, lastName, email,
                            dob.isEmpty() ? null : java.time.LocalDate.parse(dob)));
                    break;

                case "b": 
                    System.out.print("Student ID: ");
                    int studentId = Integer.parseInt(inputs.nextLine());
                    Student student = studentDAO.getStudentById(studentId);
                    if (student != null) {
                        System.out.println(student.getId() + " | " + student.getFirstName() + " " + student.getLastName()
                                + " | " + student.getEmail() + " | " + student.getDateOfBirth());
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case "c": 
                    System.out.print("Student ID to update: ");
                    int updateId = Integer.parseInt(inputs.nextLine());
                    Student updateStudent = studentDAO.getStudentById(updateId);
                    if (updateStudent != null) {
                        System.out.print("New First Name (" + updateStudent.getFirstName() + "): ");
                        String newFirst = inputs.nextLine();
                        if (!newFirst.isEmpty()) updateStudent.setFirstName(newFirst);

                        System.out.print("New Last Name (" + updateStudent.getLastName() + "): ");
                        String newLast = inputs.nextLine();
                        if (!newLast.isEmpty()) updateStudent.setLastName(newLast);

                        System.out.print("New Email (" + updateStudent.getEmail() + "): ");
                        String newEmail = inputs.nextLine();
                        if (!newEmail.isEmpty()) updateStudent.setEmail(newEmail);

                        System.out.print("New Date of Birth (" + updateStudent.getDateOfBirth() + "): ");
                        String newDob = inputs.nextLine();
                        if (!newDob.isEmpty()) updateStudent.setDateOfBirth(java.time.LocalDate.parse(newDob));

                        studentDAO.updateStudent(updateStudent);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case "d": 
                    System.out.print("Student ID to delete: ");
                    int delId = Integer.parseInt(inputs.nextLine());
                    studentDAO.deleteStudent(delId);
                    break;

                case "e":
                    System.out.print("Course Name: ");
                    String courseName = inputs.nextLine();
                    System.out.print("Course Description: ");
                    String courseDesc = inputs.nextLine();
                    courseDAO.addCourse(new Course(courseName, courseDesc));
                    break;

                case "f":
                    List<Course> courses = courseDAO.getAllCourses();
                    courses.forEach(c -> System.out.println(c.getId() + " | " + c.getCourseName() + " | " + c.getCourseDescription()));
                    break;

                case "g": 
                    System.out.print("Course ID: ");
                    int courseId = Integer.parseInt(inputs.nextLine());
                    Course course = courseDAO.getCourseById(courseId);
                    if (course != null) {
                        System.out.println(course.getId() + " | " + course.getCourseName() + " | " + course.getCourseDescription());
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;

                case "h": 
                    System.out.print("Course ID to update: ");
                    int updateCourseId = Integer.parseInt(inputs.nextLine());
                    Course updateCourse = courseDAO.getCourseById(updateCourseId);
                    if (updateCourse != null) {
                        System.out.print("New Course Name (" + updateCourse.getCourseName() + "): ");
                        String newCourseName = inputs.nextLine();
                        if (!newCourseName.isEmpty()) updateCourse.setCourseName(newCourseName);

                        System.out.print("New Course Description (" + updateCourse.getCourseDescription() + "): ");
                        String newCourseDesc = inputs.nextLine();
                        if (!newCourseDesc.isEmpty()) updateCourse.setCourseDescription(newCourseDesc);

                        courseDAO.updateCourse(updateCourse);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;

                case "i": 
                    System.out.print("Course ID to delete: ");
                    int delCourseId = Integer.parseInt(inputs.nextLine());
                    courseDAO.deleteCourse(delCourseId);
                    break;

                case "j": 
                    System.out.print("Student ID: ");
                    int mStudentId = Integer.parseInt(inputs.nextLine());
                    System.out.print("Course ID: ");
                    int mCourseId = Integer.parseInt(inputs.nextLine());
                    System.out.print("Marks: ");
                    float mMarks = Float.parseFloat(inputs.nextLine());
                    marksDAO.assignOrUpdateMark(new Marks(mStudentId, mCourseId, mMarks));
                    break;

                case "k": 
                    System.out.print("Student ID: ");
                    int sId = Integer.parseInt(inputs.nextLine());
                    List<Marks> marksList = marksDAO.getAllMarks();
                    marksList.stream().filter(m -> m.getStudentId() == sId)
                            .forEach(m -> System.out.println("Course " + m.getCourseId() + " | Marks: " + m.getMarks()));
                    break;

                case "l": 
                    System.out.print("Student ID: ");
                    int uSId = Integer.parseInt(inputs.nextLine());
                    System.out.print("Course ID: ");
                    int uCId = Integer.parseInt(inputs.nextLine());
                    System.out.print("New Marks: ");
                    float newMarks = Float.parseFloat(inputs.nextLine());
                    marksDAO.assignOrUpdateMark(new Marks(uSId, uCId, newMarks));
                    break;

                case "m": 
                    System.out.print("Student ID: ");
                    int dSId = Integer.parseInt(inputs.nextLine());
                    System.out.print("Course ID: ");
                    int dCId = Integer.parseInt(inputs.nextLine());
                    marksDAO.deleteMark(dSId, dCId);
                    break;

                case "n": 
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }

        inputs.close();
    }
}