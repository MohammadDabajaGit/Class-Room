package sample.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Lecture {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty url = new SimpleStringProperty();
    private final IntegerProperty chapterId = new SimpleIntegerProperty();

    public Lecture(int id,String name,String time,String type ,String url, int chapterId ) {
        this.setId(id);
        this.setName(name);
        this.setTime(time);
        this.setUrl(url);
        this.setType(type);
        this.setChapterId(chapterId);
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

    public String getTime() {
        return time.get();
    }
    public void setTime(String time) {
        this.time.set(time);
    }

    public String getUrl() {
        return url.get();
    }
    public void setUrl(String url) {
        this.url.set(url);
    }

    public String getType() {
        return type.get();
    }
    public void setType(String type) {
        this.type.set(type);
    }

    public int getChapterId() {
        return chapterId.get();
    }

    public IntegerProperty chapterIdProperty() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId.set(chapterId);
    }

    public Chapter getChapter() {
        return DataSource.getInstance().queryChapter("" + chapterId,"","","","").get(0);
    }
}
