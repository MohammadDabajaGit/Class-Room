package sample.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.Model.Course;
import sample.Model.CourseFactory;
import sample.Model.DataSource;
import sample.Model.Teacher;

public class TeacherController {

    Teacher teacher = new Teacher(1,"","","","");
    @FXML
    public ListView<String> courseListView;
    public ScrollPane coursePane;


    public void initialize() {
        ObservableList<Course> courses = teacher.getCourses();
        for (Course course: courses) {
            courseListView.getItems().add(course.getName());
        }
        courseListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void handleClickMainList(MouseEvent mouseEvent) {
        String name = (String) courseListView.getSelectionModel().getSelectedItem();
        Course course = DataSource.getInstance().queryCourse("", name, "", "", "" + teacher.getId(), "").get(0);
        if (course != null) {
            VBox courseView = CourseFactory.getCourseView(course);
//            Button addChapter = (Button) courseView.getChildren().get(courseView.getChildren().size()-1);
//            TextField nameField = (TextField) courseView.getChildren().get(courseView.getChildren().size()-2);
//            addChapter.setOnAction(actionEvent -> {
//                course.addChapter(nameField.getText());
//
//            });
            coursePane.setContent(courseView);
        }
    }
}
