package sample.Model;

import javafx.collections.ObservableList;

public class Admin extends User {

    private ObservableList<User> users;
    private ObservableList<Teacher> teachers;
    private ObservableList<Student> students;
    private ObservableList<Course> courses;


    public Admin(int id, String name, String email, String password, String active) {
        super(id, name, email, password, active);
        users = DataSource.getInstance().queryUser("","","","","","student");
        students = DataSource.getInstance().queryStudent("","","","","");
        teachers = DataSource.getInstance().queryTeacher("","","","","");
        courses = DataSource.getInstance().queryCourse("","","","","","");
    }

    public boolean activateUser(User user,String role) {
        return DataSource.getInstance().activateUser(user,role,"true");
    }

    public boolean deactivateUser(User user,String role) {
        return DataSource.getInstance().activateUser(user,role,"false");
    }

    public boolean activateCourse(Course course) {
        return DataSource.getInstance().activateCourse(course,"true");
    }

    public boolean deactivateCourse(Course course) {
        return DataSource.getInstance().activateCourse(course,"false");
    }

    public boolean removeUser(User user,String role) {
        boolean t = DataSource.getInstance().deleteUser(user,role);
        updateTeachers();
        updateStudent();
        return t;
    }

    public boolean removeCourse(Course course) {
        boolean t = DataSource.getInstance().deleteCourse(course);
        updateCourses();
        return t;

    }

    private void updateCourses() {
        courses = DataSource.getInstance().queryCourse("","","","","","");
    }

    private void updateTeachers() {
        teachers = DataSource.getInstance().queryTeacher("","","","","");
    }

    private void updateStudent() {
        students = DataSource.getInstance().queryStudent("","","","","");
    }

    private void updateUsers() {
        users = DataSource.getInstance().queryUser("","","","","","student");
    }

    public ObservableList<Teacher> getTeachers() {
        updateTeachers();
        return teachers;
    }

    public ObservableList<Student> getStudents() {
        updateStudent();
        return students;
    }

    public ObservableList<Course> getCourses() {
        updateCourses();
        return courses;
    }
}
