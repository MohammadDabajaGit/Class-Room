package sample.Model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CourseFactory {

    private static CourseFactory instance;
    private CourseFactory() {}

    public static VBox getCourseView(Course course){
        VBox courseView = new VBox();
        courseView.setSpacing(10);
        courseView.setAlignment(Pos.CENTER);
        for (Chapter chapter:course.getChapters()) {
            VBox chapterView = ChapterFactory.getChapterView(chapter);
            courseView.getChildren().add(chapterView);
        }
        Button addChapter = new Button("Add Chapter");
        TextField nameField = new TextField();
        nameField.setPromptText("chapter name");
        courseView.getChildren().add(nameField);
        courseView.getChildren().add(addChapter);
        addChapter.setOnAction(actionEvent -> {
                course.addChapter(nameField.getText());
            });
        return courseView;
    }

}
