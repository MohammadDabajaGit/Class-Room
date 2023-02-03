package sample.Model;

import javafx.collections.ObservableList;

public class Teacher extends User {
    ObservableList<Course> courses;
    public Teacher(int id, String name, String email, String password, String active) {
        super(id, name, email, password, active);
        courses = DataSource.getInstance().queryCourse("","","","",""+id,"");
    }

    public ObservableList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ObservableList<Course> courses) {
        this.courses = courses;
    }
}
