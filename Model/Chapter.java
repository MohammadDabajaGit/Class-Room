package sample.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Chapter {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty mark = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();
    private final IntegerProperty courseId = new SimpleIntegerProperty();
    private ObservableList<Lecture> lectures;

    public Chapter(int id,String name,String mark,String time,int courseId) {
        this.setId(id);
        this.setName(name);
        this.setMark(mark);
        this.setTime(time);
        this.setLectures(DataSource.getInstance().queryLecture("","","","","",""+id));
        this.setCourseId(courseId);
    }

    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public String getMark() {
        return mark.get();
    }
    public void setMark(String mark) {
        this.mark.set(mark);
    }

    public String getTime() {
        return time.get();
    }
    public void setTime(String time) {
        this.time.set(time);
    }

    public Course getCourse() {
        return DataSource.getInstance().queryCourse(""+courseId,"","","","","").get(0);
    }

    public int getCourseId() {
        return courseId.get();
    }

    public IntegerProperty courseIdProperty() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId.set(courseId);
    }

    public ObservableList<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(ObservableList<Lecture> lectures) {
        this.lectures = lectures;
    }
}
