package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.sql.*;

public class DataSource {

    private static final String DB_LOCATION ="jdbc:mysql://localhost:3306/" ;
    private static final String DB_NAME = "classroom";
    private static DataSource instance = new DataSource();

    private DataSource() {}

    public static DataSource getInstance() {
        return instance;
    }

    private static final String SelectStudentQuery = "SELECT * FROM classroom.student where studentId like ? and studentName like ? and studentEmail like ? and  studentPassword like ? and active like ?";
    private static final String SelectTeacherQuery = "SELECT * FROM classroom.teacher where teacherId like ? and teacherName like ? and teacherEmail like ? and  teacherPassword like ? and active like ?";
    private static final String SelectAdminQuery = "SELECT * FROM classroom.admin where adminName like ? and adminEmail like ? and  adminPassword like ?";
    private static final String SelectCourseQuery = "SELECT * FROM classroom.course where courseId like ? and courseName like ? and courseMark like ? and  courseTime like ? and courseTeacherId like ? and active like ?";
    private static final String SelectChapterQuery = "SELECT * FROM classroom.chapter where chapterId like ? and chapterName like ? and chapterMark like ? and chapterTime like ? and chapterCourseId like ?";
    private static final String SelectLectureQuery = "SELECT * FROM classroom.lectures where lectureId like ? and lectureName like ? and lectureTime like ? and lectureType like ? and lecturesUrl like ? and lectureChapterId like ?";


    private static final String InsertStudentQuery = "INSERT INTO `classroom`.`student` (`studentName`, `studentEmail`, `studentPassword`) VALUES (?,?,?)";
    private static final String InsertTeacherQuery = "INSERT INTO `classroom`.`teacher` (`teacherName`, `teacherEmail`, `teacherPassword`) VALUES (?,?,?)";
    private static final String InsertChapterQuery = "INSERT INTO `classroom`.`chapter` (`chapterName`, `chapterCourseId`) VALUES (?, ?)";

    private static final String DeactivateStudentQuery = "UPDATE `classroom`.`student` SET `active` = ? WHERE (`studentId` = ?)";
    private static final String DeactivateTeacherQuery = "UPDATE `classroom`.`teacher` SET `active` = ? WHERE (`teacherId` = ?)";
    private static final String DeactivateCourseQuery = "UPDATE `classroom`.`course` SET `active` = ? WHERE (`courseId` = ?)";

    private static final String DeleteStudentQuery = "DELETE FROM `classroom`.`student` WHERE (`studentId` = ?);";
    private static final String DeleteTeacherQuery = "DELETE FROM `classroom`.`teacher` WHERE (`teacherId` = ?);";
    private static final String DeleteCourseQuery = "DELETE FROM `classroom`.`course` WHERE (`courseId` = ?);";

    private Connection connection;
    private PreparedStatement selectStudentPreparedStatement;
    private PreparedStatement selectTeacherPreparedStatement;
    private PreparedStatement selectAdminPreparedStatement;
    private PreparedStatement selectCoursePreparedStatement;
    private PreparedStatement selectChapterPreparedStatement;
    private PreparedStatement selectLecturePreparedStatement;

    private PreparedStatement insertStudentPreparedStatement;
    private PreparedStatement insertTeacherPreparedStatement;
    private PreparedStatement insertChapterPreparedStatement;

    private PreparedStatement deactivateStudentPreparedStatement;
    private PreparedStatement deactivateTeacherPreparedStatement;
    private PreparedStatement deactivateCoursePreparedStatement;

    private PreparedStatement deleteStudentPreparedStatement;
    private PreparedStatement deleteTeacherPreparedStatement;
    private PreparedStatement deleteCoursePreparedStatement;

    public boolean open()  {
        try {
            connection = DriverManager.getConnection(DB_LOCATION+DB_NAME,"CLASS_ROOM","1234");
            selectStudentPreparedStatement = connection.prepareStatement(SelectStudentQuery);
            selectTeacherPreparedStatement = connection.prepareStatement(SelectTeacherQuery);
            selectAdminPreparedStatement = connection.prepareStatement(SelectAdminQuery);
            selectCoursePreparedStatement = connection.prepareStatement(SelectCourseQuery);
            selectChapterPreparedStatement = connection.prepareStatement(SelectChapterQuery);
            selectLecturePreparedStatement = connection.prepareStatement(SelectLectureQuery);

            insertStudentPreparedStatement = connection.prepareStatement(InsertStudentQuery);
            insertTeacherPreparedStatement = connection.prepareStatement(InsertTeacherQuery);
            insertChapterPreparedStatement = connection.prepareStatement(InsertChapterQuery);

            deactivateStudentPreparedStatement = connection.prepareStatement(DeactivateStudentQuery);
            deactivateTeacherPreparedStatement = connection.prepareStatement(DeactivateTeacherQuery);
            deactivateCoursePreparedStatement = connection.prepareStatement(DeactivateCourseQuery);

            deleteStudentPreparedStatement = connection.prepareStatement(DeleteStudentQuery);
            deleteTeacherPreparedStatement = connection.prepareStatement(DeleteTeacherQuery);
            deleteCoursePreparedStatement = connection.prepareStatement(DeleteCourseQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    public ObservableList<User> queryUser(String id,String name,String email,String password,String active,String role) {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            ResultSet result;
            if(role.equals("student")) {
                selectStudentPreparedStatement.setString(1,"%" + id);
                selectStudentPreparedStatement.setString(2,"%" + name);
                selectStudentPreparedStatement.setString(3,"%" + email);
                selectStudentPreparedStatement.setString(4,"%" + password);
                selectStudentPreparedStatement.setString(5,"%" + active);
                result = selectStudentPreparedStatement.executeQuery();
                while(result.next()) {
                    Student student = new Student(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5));
                    users.add(student);
                }
            }
            if (role.equals("teacher")) {
                selectTeacherPreparedStatement.setString(1,"%" + id);
                selectTeacherPreparedStatement.setString(2,"%" + name);
                selectTeacherPreparedStatement.setString(3,"%" + email);
                selectTeacherPreparedStatement.setString(4,"%" + password);
                selectTeacherPreparedStatement.setString(5,"%" + active);
                result = selectTeacherPreparedStatement.executeQuery();
                while(result.next()) {
                    Teacher teacher = new Teacher(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5));
                    users.add(teacher);
                }
            }
            if (role.equals("admin")) {
                selectAdminPreparedStatement.setString(1,"%" + name);
                selectAdminPreparedStatement.setString(2,"%" + email);
                selectAdminPreparedStatement.setString(3,"%" + password);
                result = selectAdminPreparedStatement.executeQuery();
                while(result.next()) {
                    Admin admin = new Admin(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),"true");
                    users.add(admin);
                }
            }

        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
        return users;
    }

    public ObservableList<Student> queryStudent(String id,String name,String email,String password,String active) {
        ObservableList<Student> students = FXCollections.observableArrayList();
        try {
            ResultSet result;
                selectStudentPreparedStatement.setString(1, "%" + id);
                selectStudentPreparedStatement.setString(2, "%" + name);
                selectStudentPreparedStatement.setString(3, "%" + email);
                selectStudentPreparedStatement.setString(4, "%" + password);
                selectStudentPreparedStatement.setString(5, "%" + active);
                result = selectStudentPreparedStatement.executeQuery();
                while (result.next()) {
                    Student student = new Student(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
                    students.add(student);
                }
            } catch(SQLException e){
                System.out.println("Query error: " + e.getMessage());
            }
        return students;
    }

    public ObservableList<Teacher> queryTeacher(String id,String name,String email,String password,String active) {
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        try {
            ResultSet result;
            selectTeacherPreparedStatement.setString(1, "%" + id);
            selectTeacherPreparedStatement.setString(2, "%" + name);
            selectTeacherPreparedStatement.setString(3, "%" + email);
            selectTeacherPreparedStatement.setString(4, "%" + password);
            selectTeacherPreparedStatement.setString(5, "%" + active);
            result = selectTeacherPreparedStatement.executeQuery();
            while (result.next()) {
                Teacher teacher = new Teacher(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
                teachers.add(teacher);
            }
        } catch(SQLException e){
            System.out.println("Query error: " + e.getMessage());
        }
        return teachers;
    }

    public ObservableList<Admin> queryAdmin(String name,String email,String password) {
        ObservableList<Admin> admins = FXCollections.observableArrayList();
        try {
            ResultSet result;
            selectAdminPreparedStatement.setString(1,"%" + name);
            selectAdminPreparedStatement.setString(2,"%" + email);
            selectAdminPreparedStatement.setString(3,"%" + password);
            result = selectAdminPreparedStatement.executeQuery();
            while(result.next()) {
                Admin admin = new Admin(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),"true");
                admins.add(admin);
            }
        } catch(SQLException e){
            System.out.println("Query error: " + e.getMessage());
        }
        return admins;
    }



    public boolean insertUser(String name,String email,String password,String role) {
        try {
            if(role.equals("student")) {
                insertStudentPreparedStatement.setString(1,name);
                insertStudentPreparedStatement.setString(2,email);
                insertStudentPreparedStatement.setString(3,password);
                insertStudentPreparedStatement.executeUpdate();
            }
            if (role.equals("teacher")) {
                insertTeacherPreparedStatement.setString(1,name);
                insertTeacherPreparedStatement.setString(2,email);
                insertTeacherPreparedStatement.setString(3,password);
                insertTeacherPreparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean insertChapter(String name,String CourseId) {
        try {
            insertChapterPreparedStatement.setString(1,name);
            insertChapterPreparedStatement.setString(2,CourseId);
            insertChapterPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean activateUser(User user,String role,String activate) {
        try {
            if (role.equals("student")) {
                deactivateStudentPreparedStatement.setString(1, activate);
                deactivateStudentPreparedStatement.setInt(2, user.getId());
                deactivateStudentPreparedStatement.executeUpdate();
            }
            if (role.equals("teacher")) {
                deactivateTeacherPreparedStatement.setString(1, activate);
                deactivateTeacherPreparedStatement.setInt(2, user.getId());
                deactivateTeacherPreparedStatement.executeUpdate();
            }
        }  catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean activateCourse(Course course,String activate) {
        try {
            deactivateCoursePreparedStatement.setString(1, activate);
            deactivateCoursePreparedStatement.setInt(2, course.getId());
            deactivateCoursePreparedStatement.executeUpdate();
        }  catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteUser(User user,String role) {
        try {
            if (role.equals("student")) {
                deleteStudentPreparedStatement.setInt(1, user.getId());
                deleteStudentPreparedStatement.executeUpdate();
            }
            if (role.equals("teacher")) {
                deleteTeacherPreparedStatement.setInt(1, user.getId());
                deleteTeacherPreparedStatement.executeUpdate();
            }
        }  catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteCourse(Course course) {
        try {
            deleteCoursePreparedStatement.setInt(1, course.getId());
            deleteCoursePreparedStatement.executeUpdate();
        }  catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public ObservableList<Course> queryCourse(String id,String name,String mark,String time, String teacherId, String active) {
        ObservableList<Course> courses = FXCollections.observableArrayList();
        try {

            selectCoursePreparedStatement.setString(1, "%" + id);
            selectCoursePreparedStatement.setString(2, "%" + name);
            selectCoursePreparedStatement.setString(3, "%" + mark);
            selectCoursePreparedStatement.setString(4, "%" + time);
            selectCoursePreparedStatement.setString(5, "%" + teacherId);
            selectCoursePreparedStatement.setString(6, "%" + active);
            ResultSet result = selectCoursePreparedStatement.executeQuery();
            while (result.next()) {
                Course course = new Course(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),result.getInt(5), result.getString(6));
                courses.add(course);
            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
        return courses;
    }

    public ObservableList<Chapter> queryChapter(String id,String name,String mark,String time, String courseId) {
        ObservableList<Chapter> chapters = FXCollections.observableArrayList();
        try {

            selectChapterPreparedStatement.setString(1, "%" + id);
            selectChapterPreparedStatement.setString(2, "%" + name);
            selectChapterPreparedStatement.setString(3, "%" + mark);
            selectChapterPreparedStatement.setString(4, "%" + time);
            selectChapterPreparedStatement.setString(5, "%" + courseId);
            ResultSet result = selectChapterPreparedStatement.executeQuery();
            while (result.next()) {
                Chapter chapter = new Chapter(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),result.getInt(5));
                chapters.add(chapter);
            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
        return chapters;
    }

    public ObservableList<Lecture> queryLecture(String id,String name,String time,String type,String url, String chapterId) {
        ObservableList<Lecture> lectures = FXCollections.observableArrayList();
        try {

            selectLecturePreparedStatement.setString(1, "%" + id);
            selectLecturePreparedStatement.setString(2, "%" + name);
            selectLecturePreparedStatement.setString(3, "%" + time);
            selectLecturePreparedStatement.setString(4, "%" + type);
            selectLecturePreparedStatement.setString(5, "%" + url);
            selectLecturePreparedStatement.setString(6, "%" + chapterId);
            ResultSet result = selectLecturePreparedStatement.executeQuery();
            while (result.next()) {
                Lecture lecture = new Lecture(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),result.getString(5),result.getInt(6));
                lectures.add(lecture);
            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
        return lectures;
    }



    public boolean close() {
        try {
            if (selectStudentPreparedStatement != null) { selectStudentPreparedStatement.close();}
            if (selectTeacherPreparedStatement != null) { selectTeacherPreparedStatement.close();}
            if (selectAdminPreparedStatement != null) { selectAdminPreparedStatement.close();}
            if (selectCoursePreparedStatement != null) { selectCoursePreparedStatement.close();}
            if (insertStudentPreparedStatement != null) { insertStudentPreparedStatement.close();}
            if (insertTeacherPreparedStatement != null) { insertTeacherPreparedStatement.close();}
            if (insertChapterPreparedStatement != null) { insertChapterPreparedStatement.close();}
            if (deactivateStudentPreparedStatement != null) { deactivateStudentPreparedStatement.close();}
            if (deactivateTeacherPreparedStatement != null) { deactivateTeacherPreparedStatement.close();}
            if (deactivateCoursePreparedStatement != null) { deactivateCoursePreparedStatement.close();}
            if (deleteStudentPreparedStatement != null) { deleteStudentPreparedStatement.close();}
            if (deleteTeacherPreparedStatement != null) { deleteTeacherPreparedStatement.close();}
            if (deleteCoursePreparedStatement != null) { deleteCoursePreparedStatement.close();}
            if (selectLecturePreparedStatement != null) { selectLecturePreparedStatement.close();}
            if (selectChapterPreparedStatement != null) { selectChapterPreparedStatement.close();}
            if (connection != null) { connection.close(); }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }



}
