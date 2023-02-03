package sample.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Course {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty mark = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();
    private final StringProperty active = new SimpleStringProperty();
    private final IntegerProperty teacherId = new SimpleIntegerProperty();
    private ObservableList<Chapter> chapters;

    public Course(int id,String name,String mark,String time,int teacherId,String active) {
        this.setId(id);
        this.setName(name);
        this.setMark(mark);
        this.setTime(time);
        this.setActive(active);
        this.setTeacherId(teacherId);
        this.setChapters(DataSource.getInstance().queryChapter("","","","",""+id));
    }

    public void addChapter(String name) {
        DataSource.getInstance().insertChapter(name,this.getId()+"");
        this.setChapters(DataSource.getInstance().queryChapter("","","","",""+this.getId()));
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

    public String getActive() {
        return active.get();
    }
    public void setActive(String active) {
        this.active.set(active);
    }

    public int getTeacherId() {
        return teacherId.get();
    }

    public IntegerProperty teacherIdProperty() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId.set(teacherId);
    }

    public Teacher getTeacher() {
        return  DataSource.getInstance().queryTeacher("" + teacherId,"","","","").get(0);
    }

    public ObservableList<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(ObservableList<Chapter> chapters) {
        this.chapters = chapters;
    }
}
