package sample.Model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChapterFactory {
    private static ChapterFactory instance;
    private static Desktop desktop = Desktop.getDesktop();

    private ChapterFactory() {};

    public static ChapterFactory getInstance() {
        return instance;
    }

    public static VBox getChapterView(Chapter chapter) {
        TreeItem chapterTreeItem = new TreeItem(chapter.getName());
        chapterTreeItem.setExpanded(true);
        int m = 0;
        for (Lecture lecture: chapter.getLectures()) {
            System.out.println(lecture.getName());
            TreeItem treeItem = new TreeItem();
            Label id = new Label(lecture.getId()+"");
            id.setVisible(false);
            Label name = new Label(lecture.getName());
            Label type = new Label("type: " + lecture.getType());
            Label time = new Label("duration: " + lecture.getTime() + " min");
            HBox hBox = new HBox(id,name,type,time);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setSpacing(10);
            treeItem.setGraphic(hBox);
            treeItem.setValue("");
            chapterTreeItem.getChildren().add(treeItem);
            m++;
        }
        chapterTreeItem.setExpanded(true);
        TreeView treeView = new TreeView(chapterTreeItem);
        treeView.setPrefHeight(25*(m+1));
        treeView.setEditable(true);
        Button addLecture = new Button("Add Lecture");

        FileChooser fileChooser = new FileChooser();
        addLecture.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog((((Node)e.getSource()).getScene()).getWindow());
                        if (file != null) {
                            openFile(file);
                        }
                    }
                });

        VBox chapterView = new VBox(treeView,addLecture);
        chapterView.setAlignment(Pos.CENTER);
        chapterView.setPrefWidth(400);
        chapterView.setPrefHeight(Region.USE_COMPUTED_SIZE);
        chapterView.setStyle("-fx-border-color: blue;");
        chapterView.setPadding(new Insets(10));
        chapterView.setSpacing(10);
        return chapterView;
    }

    private static void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
