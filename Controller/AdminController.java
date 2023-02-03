package sample.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import sample.Model.*;

public class AdminController {
    @FXML
    public TableView studentTableView;
    public TableView courseTableView;
    public TableView teacherTableView;

    Admin admin = new Admin(1,"","","","");

    @FXML
    ObservableList<Student> students;
    @FXML
    ObservableList<Teacher> teachers;
    @FXML
    ObservableList<Course> courses;
    @FXML
    ContextMenu contextMenu;
    @FXML
    ContextMenu teacherContextMenu;
    @FXML
    ContextMenu courseContextMenu;


    public void initialize() {
        students.addAll(admin.getStudents());
        teachers.addAll(admin.getTeachers());
        courses.addAll(admin.getCourses());


        contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("delete");
        MenuItem activateMenuItem = new MenuItem("activate");
        MenuItem deactivateMenuItem = new MenuItem("deactivate");
        contextMenu.getItems().addAll(deleteMenuItem,activateMenuItem,deactivateMenuItem);
        studentTableView.setContextMenu(contextMenu);

        deleteMenuItem.setOnAction(actionEvent -> {
            Student student = (Student) studentTableView.getSelectionModel().getSelectedItem();
            admin.removeUser(student, "student");
            students.setAll(admin.getStudents());
        });
        activateMenuItem.setOnAction(actionEvent -> {
            Student student = (Student) studentTableView.getSelectionModel().getSelectedItem();
            admin.activateUser(student, "student");
            students.setAll(admin.getStudents());
        });
        deactivateMenuItem.setOnAction(actionEvent -> {
            Student student = (Student) studentTableView.getSelectionModel().getSelectedItem();
            admin.deactivateUser(student, "student");
            students.setAll(admin.getStudents());
        });




        MenuItem deleteTeacherMenuItem = new MenuItem("delete");
        MenuItem activateTeacherMenuItem = new MenuItem("activate");
        MenuItem deactivateTeacherMenuItem = new MenuItem("deactivate");
        teacherContextMenu = new ContextMenu();
        teacherContextMenu.getItems().addAll(deleteTeacherMenuItem,activateTeacherMenuItem,deactivateTeacherMenuItem);
        teacherTableView.setContextMenu(teacherContextMenu);
        deleteTeacherMenuItem.setOnAction(actionEvent -> {
            Teacher teacher = (Teacher) teacherTableView.getSelectionModel().getSelectedItem();
            admin.removeUser(teacher,"teacher");
            teachers.setAll(admin.getTeachers());
        });
        activateTeacherMenuItem.setOnAction(actionEvent -> {
            Teacher teacher = (Teacher) teacherTableView.getSelectionModel().getSelectedItem();
            admin.activateUser(teacher,"teacher");
            teachers.setAll(admin.getTeachers());
        });
        deactivateTeacherMenuItem.setOnAction(actionEvent -> {
            Teacher teacher = (Teacher) teacherTableView.getSelectionModel().getSelectedItem();
            admin.deactivateUser(teacher,"teacher");
            teachers.setAll(admin.getTeachers());
        });


        MenuItem deleteCourseMenuItem = new MenuItem("delete");
        MenuItem activateCourseMenuItem = new MenuItem("activate");
        MenuItem deactivateCourseMenuItem = new MenuItem("deactivate");
        courseContextMenu = new ContextMenu();
        courseContextMenu.getItems().addAll(deleteCourseMenuItem,activateCourseMenuItem,deactivateCourseMenuItem);
        courseTableView.setContextMenu(courseContextMenu);
        deleteCourseMenuItem.setOnAction(actionEvent -> {
            Course course = (Course) courseTableView.getSelectionModel().getSelectedItem();
            admin.removeCourse(course);
            courses.setAll(admin.getCourses());
        });
        activateCourseMenuItem.setOnAction(actionEvent -> {
            Course course = (Course) courseTableView.getSelectionModel().getSelectedItem();
            admin.activateCourse(course);
            courses.setAll(admin.getCourses());
        });
        deactivateCourseMenuItem.setOnAction(actionEvent -> {
            Course course = (Course) courseTableView.getSelectionModel().getSelectedItem();
            admin.deactivateCourse(course);
            courses.setAll(admin.getCourses());
        });

    }
}
